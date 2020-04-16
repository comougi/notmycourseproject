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

    public static Book getBook(Node node) {
        Book book = new Book();
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            book.setTitle(getTagValue("title", element));
            book.setAuthor(getTagValue("author", element));
            book.setGenre(getTagValue("genre", element));
            book.setAmount(Integer.parseInt(getTagValue("amount", element)));
            book.setInShop(Boolean.parseBoolean(getTagValue("inShop", element)));

        }

        return book;
    }

    public static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }
}
