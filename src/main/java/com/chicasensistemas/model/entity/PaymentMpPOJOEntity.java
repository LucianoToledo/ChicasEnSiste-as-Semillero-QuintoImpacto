package com.chicasensistemas.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mercadopago.resources.payment.PaymentAdditionalInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;



@Getter
@Setter
@NoArgsConstructor
@Entity(name = "PaymentMp")
public class PaymentMpPOJOEntity {

    @Id
    /** Payment ID. */
    private Long id;

    /**
     * Creation date.
     */
    @Column(name = "date_created")
    private String dateCreated;

    @Column(name = "date_last_updated")
    /** Last modified date. */
    private String dateLastUpdated;

    @Column(name = "date_of_expiration")
    /** Date of expiration. */
    private String dateOfExpiration;

    @Column(name = "money_release_date")
    private String moneyReleaseDate;

    //PaymentOrder
    @Column(name = "order_id")
    /** Id of the associated purchase order. */
    private Long orderId;

    @Column(name = "order_type")
    /** Order type. */
    private String orderType;

    //PaymentPayer
    @Column(name = "payer_id")
    /** Payer's ID. */
    private String payerId;

    @Column(name = "payer_type_identification")
    /** Type of identification. */
    private String payerTypeIdentification;

    @Column(name = "payer_number_identification")
    /** Unique number of that identification. */
    private String payerNumberIdentication;

    /**
     * Payment method chosen to do the payment.
     */
    @Column(name = "payment_method_id")
    private String paymentMethodId;

    /**
     * Payment type.
     */
    @Column(name = "payment_type_id")
    private String paymentTypeId;

    //PaymentTransactionDetails
    /** Amount received by the seller.*/
    @Column(name = "net_received_amount")
    private String netReceivedAmount;

    /**Total amount paid by the buyer (includes fees).*/
    @Column(name = "total_paid_amount")
    private BigDecimal totalPaidAmount;

    /**Total installments amount.*/
    @Column(name = "installment_amount") //TODO: corregir mal escrito "installemnt
    private BigDecimal installmentAmount;

    /** Status. */
    private String status;

    /** Status detail. */
    @Column(name = "status_detail")
    private String statusDetail;

    /** Fee type. */
    @Column(name = "fee_type")
    private String feeType;

    /** Who absorbs the cost. */
    @Column(name = "fee_payer")
    private String feePayer;

    /** Fee amount. */
    @Column(name = "fee_amount")
    private BigDecimal feeAmount;

    /** Last four digits of card number. */
    @Column(name = "last_four_digits")
    private String lastFourDigits;

    /** Payment reason or item title. */
    private String description;

    /** Selected quantity of installments. */
    private int installments;

    /** Amount paid. */
    @Column(name = "transaction_amount")
    private BigDecimal transactionAmount;

    /** Total refunded amount. */
    @Column(name = "transaction_amount_refunded")
    private BigDecimal transactionAmountRefunded;

    @Column(name = "currency_id")
    private String currencyId;

    @Column(name = "item_id")
    private String itemId;
}
