package com.og.MainClasses;

import com.og.Controllers.AdminScreenController;
import com.og.FXMLService;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.List;

public class XMLAdd {
    public static void addProduct(String model, String brand, String type) {

        File xmlFile = new File("src/com/og/XMLs/Products");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);
            document.getDocumentElement().normalize();
            Element nList = document.getDocumentElement();

            Element newProduct = document.createElement("product");

            Element newModel = document.createElement("model");
            newModel.appendChild(document.createTextNode(model));
            newProduct.appendChild(newModel);

            Element newBrand = document.createElement("brand");
            newBrand.appendChild(document.createTextNode(brand));
            newProduct.appendChild(newBrand);

            Element newType = document.createElement("type");
            newType.appendChild(document.createTextNode(type));
            newProduct.appendChild(newType);

            Element newAmount = document.createElement("amount");
            newAmount.appendChild(document.createTextNode(Integer.toString(1)));
            newProduct.appendChild(newAmount);

            Element newInShop = document.createElement("inShop");
            newInShop.appendChild(document.createTextNode(Boolean.toString(true)));
            newProduct.appendChild(newInShop);


            nList.appendChild(newProduct);

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            StreamResult result = new StreamResult(new File("src/com/og/XMLs/Products"));
            DOMSource source = new DOMSource(document);
            transformer.transform(source, result);


            AdminScreenController adminScreenController = FXMLService.loadScreenReturnController("AdminScreen");
            adminScreenController.showAllProducts();
            adminScreenController.userInfo();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void addSale(String model, int amount) {

        File xmlFile = new File("src/com/og/XMLs/SaleInfo.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);
            document.getDocumentElement().normalize();
            Element nList = document.getDocumentElement();

            Element newSale = document.createElement("product");

            Element newModel = document.createElement("model");
            newModel.appendChild(document.createTextNode(model));
            newSale.appendChild(newModel);


            Element newLeft = document.createElement("amount");
            newLeft.appendChild(document.createTextNode(Integer.toString(amount - 1)));
            newSale.appendChild(newLeft);


            nList.appendChild(newSale);

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            StreamResult result = new StreamResult(new File("src/com/og/XMLs/SaleInfo.xml"));
            DOMSource source = new DOMSource(document);
            transformer.transform(source, result);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void addIn(String model, String brand, String type, int amount,boolean inShop, String file, String tag) {
        File xmlFile = new File(String.format("src/com/og/XMLs/%s", file));
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);
            document.getDocumentElement().normalize();
            Element nList = document.getDocumentElement();

            Element newNeed = document.createElement(tag);

            Element newModel = document.createElement("model");
            newModel.appendChild(document.createTextNode(model));
            newNeed.appendChild(newModel);

            Element newBrand = document.createElement("brand");
            newBrand.appendChild(document.createTextNode(brand));
            newNeed.appendChild(newBrand);

            Element newType = document.createElement("type");
            newType.appendChild(document.createTextNode(type));
            newNeed.appendChild(newType);

            Element newInShop = document.createElement("inShop");
            newInShop.appendChild(document.createTextNode(Boolean.toString(inShop)));
            newNeed.appendChild(newInShop);

            Element newAmount = document.createElement("amount");
            newAmount.appendChild(document.createTextNode(Integer.toString(amount)));
            newNeed.appendChild(newAmount);


            nList.appendChild(newNeed);

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            StreamResult result = new StreamResult(new File(String.format("src/com/og/XMLs/%s", file)));
            DOMSource source = new DOMSource(document);
            transformer.transform(source, result);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }


    public static void addFromStockToShop() {
        List<Product> deliveryProducts = XMLReturn.returnProductsIn("Delivery", "product");
        for (int i = 0; i < deliveryProducts.size(); i++) {
            addProduct(deliveryProducts.get(i).getModel(), deliveryProducts.get(i).getBrand(), deliveryProducts.get(i).getType());
        }


    }

    public static void addUser(String login, String password, String position, String fullName) {

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


            FXMLService.loadScreen("StartScreen");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }


}
