package com.og.MainClasses;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLGet {
    public static User getUser(Node node) {
        User user = new User();
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            user.setLogin(getTagValue("login", element));
            user.setPassword(getTagValue("password", element));
            user.setPosition(getTagValue("position", element));
            user.setFullName(getTagValue("fullName", element));
        }

        return user;
    }

    public static Product getProduct(Node node) {
        Product product = new Product();
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            product.setModel(getTagValue("model", element));
            product.setBrand(getTagValue("brand", element));
            product.setType(getTagValue("type", element));
            product.setAmount(Integer.parseInt(getTagValue("amount", element)));
            product.setInShop(Boolean.parseBoolean(getTagValue("inShop", element)));

        }

        return product;
    }

    public static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }
}
