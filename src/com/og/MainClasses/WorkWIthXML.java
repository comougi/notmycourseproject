package com.og.MainClasses;

import com.og.FXMLHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class WorkWIthXML {


    // создаем из узла документа объект Language
    private static User getUser(Node node) {
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

    // получаем значение элемента по указанному тегу
    private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }

    public void read(String login, String password) {
        File xmlFile = new File("src/com/og/XMLs/Users.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);
            document.getDocumentElement().normalize();
            System.out.println("Корневой элемент: " + document.getDocumentElement().getNodeName());
            // получаем узлы с именем User
            // теперь XML полностью загружен в память
            // в виде объекта Document
            NodeList nodeList = document.getElementsByTagName("user");

            // создадим из него список объектов User
            List<User> userList = new ArrayList<User>();
            for (int i = 0; i < nodeList.getLength(); i++) {
                userList.add(getUser(nodeList.item(i)));
            }

            User u = new User(login, password);
            for (int i = 0; i < userList.size(); i++) {
                if (userList.get(0).equals(u)) {
                    FXMLHelper.loadScreen("RegistrationScreen");
                }
            }
            // печатаем в консоль информацию по каждому объекту User


            for (User user : userList) {
                System.out.println(user.toString());
            }


        } catch (Exception exc) {
            exc.printStackTrace();
        }

    }
}
