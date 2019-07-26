package org.web3j.protocol.core;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

import org.web3j.protocol.core.methods.response.*;

public class EqualsVerifierResponseTest {

    @Test
    public void testBlock() {
        EqualsVerifier.forClass(PlatonBlock.Block.class)
                .suppress(Warning.NONFINAL_FIELDS)
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();
    }

    @Test
    public void testTransaction() {
        EqualsVerifier.forClass(Transaction.class)
                .suppress(Warning.NONFINAL_FIELDS)
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();
    }

    @Test
    public void testTransactionReceipt() {
        EqualsVerifier.forClass(TransactionReceipt.class)
                .suppress(Warning.NONFINAL_FIELDS)
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();
    }

    @Test
    public void testLog() {
        EqualsVerifier.forClass(Log.class)
                .suppress(Warning.NONFINAL_FIELDS)
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();
    }

    @Test
    public void testSshMessage() {
        EqualsVerifier.forClass(ShhMessages.SshMessage.class)
                .suppress(Warning.NONFINAL_FIELDS)
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();
    }

    @Test
    public void testSyncing() {
        EqualsVerifier.forClass(PlatonSyncing.Syncing.class)
                .suppress(Warning.NONFINAL_FIELDS)
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();
    }

    @Test
    public void testAbiDefinition() {
        EqualsVerifier.forClass(AbiDefinition.class)
                .suppress(Warning.NONFINAL_FIELDS)
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();
    }

    @Test
    public void testAbiDefinitionNamedType() {
        EqualsVerifier.forClass(AbiDefinition.NamedType.class)
                .suppress(Warning.NONFINAL_FIELDS)
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();
    }

    @Test
    public void testHash() {
        EqualsVerifier.forClass(PlatonLog.Hash.class)
                .suppress(Warning.NONFINAL_FIELDS)
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();
    }

    @Test
    public void testTransactionHash() {
        EqualsVerifier.forClass(PlatonBlock.TransactionHash.class)
                .suppress(Warning.NONFINAL_FIELDS)
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();
    }

    @Test
    public void testError() {
        EqualsVerifier.forClass(Response.Error.class)
                .suppress(Warning.NONFINAL_FIELDS)
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();
    }
}
