package com.hedera.hashgraph.sdk;

import com.google.protobuf.InvalidProtocolBufferException;
import com.hedera.hashgraph.sdk.proto.TokenGrantKycTransactionBody;
import com.hedera.hashgraph.sdk.proto.Transaction;
import com.hedera.hashgraph.sdk.proto.TransactionBody;
import com.hedera.hashgraph.sdk.proto.TransactionResponse;
import com.hedera.hashgraph.sdk.proto.TokenServiceGrpc;
import io.grpc.MethodDescriptor;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class TokenGrantKycTransaction extends com.hedera.hashgraph.sdk.Transaction<TokenGrantKycTransaction> {
    private final TokenGrantKycTransactionBody.Builder builder;

    public TokenGrantKycTransaction() {
        builder = TokenGrantKycTransactionBody.newBuilder();
    }

    TokenGrantKycTransaction(LinkedHashMap<TransactionId, LinkedHashMap<AccountId, com.hedera.hashgraph.sdk.proto.Transaction>> txs) throws InvalidProtocolBufferException {
        super(txs);

        builder = bodyBuilder.getTokenGrantKyc().toBuilder();
    }

    public TokenId getTokenId() {
        return TokenId.fromProtobuf(builder.getToken());
    }

    public TokenGrantKycTransaction setTokenId(TokenId tokenId) {
        requireNotFrozen();
        builder.setToken(tokenId.toProtobuf());
        return this;
    }

    public AccountId getAccountId() {
        return AccountId.fromProtobuf(builder.getAccount());
    }

    public TokenGrantKycTransaction setAccountId(AccountId accountId) {
        requireNotFrozen();
        builder.setAccount(accountId.toProtobuf());
        return this;
    }

    @Override
    MethodDescriptor<Transaction, TransactionResponse> getMethodDescriptor() {
        return TokenServiceGrpc.getGrantKycToTokenAccountMethod();
    }

    @Override
    boolean onFreeze(TransactionBody.Builder bodyBuilder) {
        bodyBuilder.setTokenGrantKyc(builder);
        return true;
    }
}
