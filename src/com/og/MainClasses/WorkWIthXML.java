package com.og.MainClasses;

import com.og.FXMLHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class WorkWIthXML {

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

    private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }

    public void validation(String login, String password) {
        File xmlFile = new File("src/com/og/XMLs/Users.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);
            document.getDocumentElement().normalize();
            NodeList nodeList = document.getElementsByTagName("user");

            List<User> userList = new ArrayList<User>();
            for (int i = 0; i < nodeList.getLength(); i++) {
                userList.add(getUser(nodeList.item(i)));
            }

            User u = new User(login, password);
            for (int i = 0; i < userList.size(); i++) {
                if (u.getLogin().equals(userList.get(i).getLogin()) && u.getPassword().equals(userList.get(i).getPassword())) {
                    if (userList.get(i).getPosition().equals("admin")) {
                        FXMLHelper.loadScreen("AdminScreen");
                    } else if (userList.get(i).getPosition().equals("seller")) {
                        FXMLHelper.loadScreen("SellerScreen");
                    } else if (userList.get(i).getPosition().equals("store keeper")) {
                        FXMLHelper.loadScreen("StoreKeeperScreen");
                    }
                }
            }

        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    public void addUser(String login, String password, String position, String fullName) {

        File xmlFile = new File("src/com/og/XMLs/Users.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);
            document.getDocumentElement().normalize();
            Element nList = document.getDocumentElement();

            Element newUser = document.createElement("user");

            Element newLogin = document.createElement("login");
            newLogin.appendChild(document.createTextNode(login));
            newUser.appendChild(newLogin);

            Element newPassword = document.createElement("password");
            newPassword.appendChild(document.createTextNode(password));
            newUser.appendChild(newPassword);

            Element newPosition = document.createElement("position");
            newPosition.appendChild(document.createTextNode(position));
            newUser.appendChild(newPosition);

            Element newFullName = document.createElement("fullName");
            newFullName.appendChild(document.createTextNode(fullName));
            newUser.appendChild(newFullName);

            nList.appendChild(newUser);

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            StreamResult result = new StreamResult(new File("src/com/og/XMLs/Users.xml"));
            DOMSource source = new DOMSource(document);
            transformer.transform(source, result);


            FXMLHelper.loadScreen("StartScreen");
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }
}
