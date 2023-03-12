package com.chicasensistemas.model.response.MP;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mercadopago.client.cardtoken.CardTokenClient;
import com.mercadopago.resources.CardToken;
import com.mercadopago.resources.payment.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
public class PaymentResponse {
    @JsonProperty("userId")
    private String userId;
    private Long id;

    @JsonProperty("additional_info")
    private PaymentAdditionalInfo additionalInfo;

    @JsonProperty("date_created")
    private String dateCreated;

    @JsonProperty("date_aproved")
    private String dateApproved;

    @JsonProperty("date_last_updated")
    private String dateLastUpdated;

    @JsonProperty("date_of_expiration")
    private String dateOfExpiration;

    @JsonProperty("money_release_date")
    private String moneyReleaseDate;

    @JsonProperty("money_release_schema")
    private String moneyReleaseSchema;

    @JsonProperty("operationType")
    private String operationType;

//    @JsonProperty("payment_method_id")
//    private String paymentMethodId;

    @JsonProperty("payment_type_id")
    private String paymentTypeId;

    private String status;

    @JsonProperty("status_detail")
    private String statusDetail;

    @JsonProperty("currency_id")
    private String currencyId;

    private String description;

    @JsonProperty("payer")
    private PaymentPayer payer;

    private Map<String, Object> metadata;

//    @JsonProperty("")
//    private PaymentAdditionalInfo additionalInfo;

    private PaymentCard card;

    private PaymentOrder order;

    @JsonProperty("transaction_amount")
    private BigDecimal transactionAmount;

    @JsonProperty("transaction_amount_refunded")
    private BigDecimal transactionAmountRefunded;

    private int installments;

    @JsonProperty("transaction_details")
    private PaymentTransactionDetails transactionDetails;

    @JsonProperty("fee_details")
    private List<PaymentFeeDetail> feeDetails;

    @JsonProperty("payment_method_id")
    private String paymentMethodOptionId;

    private String couponCode;

    @JsonProperty("refunds")
    private List<PaymentRefund> refunds;
}
