package com.chicasensistemas.service;

import com.chicasensistemas.model.entity.CourseEntity;
import com.chicasensistemas.model.entity.PaymentMpPOJOEntity;
import com.chicasensistemas.model.entity.UserEntity;
import com.chicasensistemas.model.request.InscriptionRequest;
import com.chicasensistemas.model.request.PaymentRequest;
import com.chicasensistemas.model.response.MP.PaymentNotification;
import com.chicasensistemas.model.response.MP.PaymentResponse;
import com.chicasensistemas.model.response.MP.PaymentUrlResponse;
import com.chicasensistemas.repository.IPaymentMpRepository;
import com.chicasensistemas.service.abstraction.IMercadoPagoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.common.AddressRequest;
import com.mercadopago.client.preference.*;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import javax.persistence.EntityExistsException;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MercadoPagoService implements IMercadoPagoService {

    @Autowired
    CourseServiceImpl courseService;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    IPaymentMpRepository paymentMpRepository;

    @Autowired
    InscriptionServiceImpl inscriptionService;

    //@Value("${ACCESS_TOKEN_MP}")
    private String ACCESS_TOKEN_MP="TEST-7857416450219336-071413-ec4b75fe62677e6d3599d1ad594c2895-481246687";

    /**
     * As MercadoPago return us a payment notification, we need to make our http://localhost:8080 in a https url
     * Ngrok help us to solve this problem, https://ngrok.com/
     * Once we initialize Ngrok, we can open http://localhost:4040 to see all the requests
     */
    //@Value("${URL_NGROK}")
    private String URL_NGROK="https://be9a-170-79-182-254.sa.ngrok.io";

    private final ObjectMapper mapper = new ObjectMapper();

    public PaymentUrlResponse createPreference(PaymentRequest paymentRequest) throws MPException, MPApiException {

        UserEntity userEntity = userService.getByIdAndEnabled(paymentRequest.getIdUser());
        CourseEntity courseEntity = courseService.getByIdAndSoftDeleteFalse(paymentRequest.getIdCourse());
        inscriptionService.validateInscription(userEntity, courseEntity);

        MercadoPagoConfig.setAccessToken(ACCESS_TOKEN_MP);

        PreferenceRequest request = PreferenceRequest.builder()
                .items(items(courseEntity))
                .payer(payer(userEntity))
                .binaryMode(true)
                .statementDescriptor("Chicas En Sistemas")
                .paymentMethods(PreferencePaymentMethodsRequest.builder()
                        .excludedPaymentTypes(createExcludedPaymentTypes())
                        .installments(3)
                        .build())
                .backUrls(PreferenceBackUrlsRequest.builder()
                        .success(URL_NGROK + "/success")
                        .failure(URL_NGROK + "/failure")
                        .build())
                .notificationUrl(URL_NGROK + "/mp/notification")
                .build();

        PreferenceClient client = new PreferenceClient();
        return new PaymentUrlResponse(client.create(request).getSandboxInitPoint());
    }

    private PreferencePayerRequest payer(UserEntity userEntity) {
        return PreferencePayerRequest.builder()
                .name(userEntity.getFirstName())
                .surname(userEntity.getLastName())
                .email(userEntity.getEmail())
                .address(AddressRequest.builder()
                        .streetName(userEntity.getUserId()) //We use streetName to save the userID because the PreferencePayerRequest hasn't a field to save it, in createPaymentResponse() we need de userId
                        .build())
                .build();
    }

    private List<PreferenceItemRequest> items(CourseEntity courseEntity) {
        PreferenceItemRequest item =
                PreferenceItemRequest.builder()
                        .id(courseEntity.getCourseId())
                        .title(courseEntity.getNameCourse())
                        .description(courseEntity.getDescription())
                        .quantity(1)
                        .unitPrice(new BigDecimal(courseEntity.getPrice()))
                        .build();
        List<PreferenceItemRequest> preferenceItemRequests = new ArrayList<>();
        preferenceItemRequests.add(item);
        return preferenceItemRequests;
    }

    private List<PreferencePaymentTypeRequest> createExcludedPaymentTypes() {
        List<PreferencePaymentTypeRequest> excludedPaymentTypes = new ArrayList<>();
        excludedPaymentTypes.add(PreferencePaymentTypeRequest.builder().id("ticket").build());
        return excludedPaymentTypes;
    }

    @Async
    private ObjectNode connectionMpPayment(String idPayment) throws IOException {
        URL url = new URL("https://api.mercadopago.com/v1/payments/" + idPayment);
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setRequestProperty("Accept", "application/json");
        http.setRequestProperty("Authorization", "Bearer " + ACCESS_TOKEN_MP);
        http.setRequestMethod("GET");
        JsonNode node = mapper.readTree(http.getInputStream());
        return mapper.readValue(node.toString(), ObjectNode.class);
    }

    @Async
    private PaymentResponse createPaymentResponse(ObjectNode objectNode) throws JsonProcessingException {
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        PaymentResponse paymentResponse = mapper.readValue(objectNode.toString(), PaymentResponse.class);
        paymentResponse.setUserId(objectNode
                .get("additional_info")
                .get("payer")
                .get("address")
                .get("street_name").toString()); //userId, see the reference in payment() method
        return paymentResponse;
    }

    /**
     * @param notificationJson
     * @throws IOException
     * When we receive the MP notification after payment, this method will be executed to process it
     */

    @Async
    public void notificationMP(String notificationJson) throws IOException {
        PaymentNotification paymentNotification;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        if (!notificationJson.contains("resource")) {
            try {
                paymentNotification = gson.fromJson(notificationJson, PaymentNotification.class);
                String idPayment = paymentNotification.getData().getId();
                savePayment(createPaymentResponse(connectionMpPayment(idPayment)));
            } catch (IOException e) {
                throw new IOException(e);
            } catch (MPException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void savePayment(PaymentResponse notification) throws MPException {
        validatePaymentPojo(notification.getId());

        PaymentMpPOJOEntity entity = new PaymentMpPOJOEntity();
        entity.setId(notification.getId());
        entity.setItemId(notification.getAdditionalInfo().getItems().get(0).getId());
        entity.setDateCreated(notification.getDateCreated());
        entity.setDateLastUpdated(notification.getDateLastUpdated());
        entity.setMoneyReleaseDate(notification.getMoneyReleaseDate());
        entity.setCurrencyId(notification.getCurrencyId());
        entity.setOrderId(notification.getOrder().getId());
        entity.setOrderType(notification.getOrder().getType());

        String userId = notification.getUserId();
        entity.setPayerId(userId.substring(1, userId.length() - 1));

        entity.setPayerTypeIdentification(notification.getPayer().getIdentification().getType());
        entity.setPayerNumberIdentication((notification.getPayer().getIdentification().getNumber()));
        entity.setDateOfExpiration(notification.getDateOfExpiration());
        entity.setPaymentMethodId(notification.getPaymentMethodOptionId());
        entity.setPaymentTypeId(notification.getPaymentTypeId());
        entity.setStatus(notification.getStatus());
        entity.setStatusDetail(notification.getStatusDetail());
        entity.setLastFourDigits(notification.getCard().getLastFourDigits());//check
        entity.setDescription(notification.getDescription());
        entity.setInstallments(notification.getInstallments());
        entity.setTransactionAmount(notification.getTransactionAmount());
        entity.setTransactionAmountRefunded(notification.getTransactionAmountRefunded());
        if (notification.getFeeDetails().toString().length() > 2) {
            entity.setFeePayer(notification.getFeeDetails().get(0).getFeePayer());
            entity.setFeeType(notification.getFeeDetails().get(0).getType());
            entity.setFeeAmount(notification.getFeeDetails().get(0).getAmount());
        }
        createInscription(paymentMpRepository.save(entity));
    }

    private void createInscription(PaymentMpPOJOEntity paymentMpPOJO) throws MPException {
        if (paymentMpPOJO.getStatus().equalsIgnoreCase("approved")) {
            inscriptionService.createInscription(
                    new InscriptionRequest(paymentMpPOJO.getItemId(), paymentMpPOJO.getPayerId()));
        } else {
            throw new MPException("Su pago no pudo ser procesado, \n" +
                    "Código de inscripcion/pago: " + paymentMpPOJO.getId() + " \n" +
                    "Status: " + paymentMpPOJO.getStatus() + " \n" +
                    "Description: " + paymentMpPOJO.getStatusDetail());
        }
    }

    private void validatePaymentPojo(Long id) {
        Optional<PaymentMpPOJOEntity> paymentMpPOJO = paymentMpRepository.findById(id);
        if (paymentMpPOJO.isPresent() && paymentMpPOJO.get().getStatus().equalsIgnoreCase("approved")) {
            throw new EntityExistsException("The payment already exist");
        }
    }
}

