/**
 * Copyright 2009-2018 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.ibatis.reflection.factory;

import org.apache.ibatis.reflection.ReflectionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * DefaultObjectFactoryTest
 *
 * @author Ryan Lamore
 */
public class DefaultObjectFactoryTest {

    @Test
    public void createClass() throws Exception {
        DefaultObjectFactory defaultObjectFactory = new DefaultObjectFactory();
        TestClass testClass = defaultObjectFactory.create(TestClass.class,
                Arrays.<Class<?>>asList(String.class, Integer.class), Arrays.<Object>asList("foo", 0));

        Assertions.assertEquals((Integer) 0, testClass.myInteger, "myInteger didn't match expected");
        Assertions.assertEquals("foo", testClass.myString, "myString didn't match expected");
    }

    @Test
    public void createClassThrowsProperErrorMsg() {
        DefaultObjectFactory defaultObjectFactory = new DefaultObjectFactory();
        try {
            defaultObjectFactory.create(TestClass.class, Collections.<Class<?>>singletonList(String.class), Collections.<Object>singletonList("foo"));
            Assertions.fail("Should have thrown ReflectionException");
        } catch (Exception e) {
            Assertions.assertTrue(e instanceof ReflectionException, "Should be ReflectionException");
            Assertions.assertTrue(e.getMessage().contains("(String)"), "Should not have trailing commas in types list");
            Assertions.assertTrue(e.getMessage().contains("(foo)"), "Should not have trailing commas in values list");
        }
    }

    @Test
    public void creatHashMap() throws Exception {
        DefaultObjectFactory defaultObjectFactory = new DefaultObjectFactory();
        Map map = defaultObjectFactory.create(Map.class, null, null);
        Assertions.assertTrue(map instanceof HashMap, "Should be HashMap");
    }

    @Test
    public void createArrayList() throws Exception {
        DefaultObjectFactory defaultObjectFactory = new DefaultObjectFactory();
        List list = defaultObjectFactory.create(List.class);
        Assertions.assertTrue(list instanceof ArrayList, " list should be ArrayList");

        Collection collection = defaultObjectFactory.create(Collection.class);
        Assertions.assertTrue(collection instanceof ArrayList, " collection should be ArrayList");

        Iterable iterable = defaultObjectFactory.create(Iterable.class);
        Assertions.assertTrue(iterable instanceof ArrayList, " iterable should be ArrayList");
    }

    @Test
    public void createTreeSet() throws Exception {
        DefaultObjectFactory defaultObjectFactory = new DefaultObjectFactory();
        SortedSet sortedSet = defaultObjectFactory.create(SortedSet.class);
        Assertions.assertTrue(sortedSet instanceof TreeSet, " sortedSet should be TreeSet");
    }

    @Test
    public void createHashSet() throws Exception {
        DefaultObjectFactory defaultObjectFactory = new DefaultObjectFactory();
        Set set = defaultObjectFactory.create(Set.class);
        Assertions.assertTrue(set instanceof HashSet, " set should be HashSet");
    }
}
