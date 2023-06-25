package com.acmebank.accountmanager.domainobject;

import com.acmebank.accountmanager.domainvalue.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name ="transaction")
@Table(name = "TRANSACTION")
public class Transaction {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String transactionId;
    private String fromAccountNumber;
    private String toAccountNumber;
    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;

}
