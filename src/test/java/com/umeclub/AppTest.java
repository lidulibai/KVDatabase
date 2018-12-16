package com.umeclub;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Unit test for simple App.
 */
public class AppTest {
    Logger logger = LoggerFactory.getLogger(AppTest.class);

    @Test
    public void testNull() {
        KeyObject key = new KeyObject();
        key.setId(1234L);
        key.setHash(new String("hello").getBytes());
        logger.info("KeyObject1: " + key.toString());
        logger.info("id: " + key.getId());
        logger.info("hash: " + Arrays.toString(key.getHash()));

        KeyObject key1 = new KeyObject(12L, new String("213").getBytes());
        logger.info("KeyObject2: " + key1.toString());
        String val1 = new String("value");

        Database database = new Database();

        database.put(key, new String("world").getBytes());
        database.put(key1, val1.getBytes());

        logger.info(new String(database.get(key)));
        logger.info(new String(database.get(1234L)));
        logger.info(new String(database.get(new String("hello").getBytes())));

        assertEquals(val1, new String(database.get(key1)));
        assertEquals(val1, new String(database.get(12L)));
        assertEquals(val1, new String(database.get(new String("213").getBytes())));

        assertEquals(null, database.get(4545L));

    }
}
