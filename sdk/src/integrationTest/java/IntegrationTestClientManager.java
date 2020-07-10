import com.google.errorprone.annotations.Var;
import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.Client;
import com.hedera.hashgraph.sdk.PrivateKey;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class IntegrationTestClientManager {
    IntegrationTestClientManager() {

    }

    public static Client getClient() {
        @Var Client client;

        try {
            client = Client.fromJsonFile(System.getProperty("CONFIG_FILE"));
            System.out.println("Using client from config file");
        } catch (Exception e) {
            System.out.println("Failed to use client network. Using testnet instead.");
            client = Client.forTestnet();
        }

        try {
            var operatorKey = PrivateKey.fromString(System.getProperty("OPERATOR_KEY"));
            var operatorId = AccountId.fromString(System.getProperty("OPERATOR_ID"));

            client.setOperator(operatorId, operatorKey);
        } catch (Exception e) {
            System.out.println("Did not find `OPERATOR_KEY` or `OPERATOR_ID` environment variables.");
            System.out.println("Using operator within the config.");
        }

        assertNotNull(client.getOperatorId());
        assertNotNull(client.getOperatorKey());

        return client;
    }
}