package com.og.MainClasses;

import com.og.Controllers.AdminScreenController;
import com.og.Controllers.SellerScreenController;
import com.og.Controllers.StoreKeeperScreenController;
import com.og.FXMLService;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static com.og.MainClasses.XMLGet.getProduct;
import static com.og.MainClasses.XMLGet.getUser;

public class XMLReturn {


    public static List<Product> returnProductsInShop() {
        List<Product> productList = returnProductsIn("Products", "product");
        List<Product> productInShopList = new ArrayList<Product>();
        try {
            for (int i = 0; i < productList.size(); i++) {
                if (productList.get(i).isInShop() && productList.get(i).getAmount() != 0) {
                    productInShopList.add(productList.get(i));
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return productInShopList;
    }

    public static List<Product> returnProductsIn(String file, String tag) {
        File xmlFile = new File(String.format("src/com/og/XMLs/%s", file));
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        List<Product> productList = new ArrayList<Product>();
        try {
            builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);
            document.getDocumentElement().normalize();
            NodeList nodeList = document.getElementsByTagName(tag);


            for (int i = 0; i < nodeList.getLength(); i++) {
                productList.add(getProduct(nodeList.item(i)));
            }

        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return productList;
    }


    public static List<User> returnUsers() {
        File xmlFile = new File("src/com/og/XMLs/Users.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        List<User> userList = new ArrayList<User>();
        try {
            builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);
            document.getDocumentElement().normalize();
            NodeList nodeList = document.getElementsByTagName("user");


            for (int i = 0; i < nodeList.getLength(); i++) {
                userList.add(getUser(nodeList.item(i)));
            }

        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return userList;
    }

    public static void validation(String login, String password) {
        User u = new User(login, password);
        List<User> userList = returnUsers();

        for (int i = 0; i < userList.size(); i++) {
            if (u.getLogin().equals(userList.get(i).getLogin()) && u.getPassword().equals(userList.get(i).getPassword())) {
                User.activeUser = userList.get(i);
                if (userList.get(i).getPosition().equals("admin")) {
                    AdminScreenController adminScreenController = FXMLService.loadScreenReturnController("AdminScreen");
                    adminScreenController.showAllProducts();
                    adminScreenController.userInfo();
                } else if (userList.get(i).getPosition().equals("seller")) {
                    SellerScreenController sellerScreenController = FXMLService.loadScreenReturnController("SellerScreen");
                    sellerScreenController.userInfo();
                    sellerScreenController.showAllProductsInShop();
                } else if (userList.get(i).getPosition().equals("store keeper")) {
                    StoreKeeperScreenController storeKeeperScreenController = FXMLService.loadScreenReturnController("StoreKeeperScreen");
                    storeKeeperScreenController.showProductsInStock();
                    storeKeeperScreenController.showNeedItList();
                    storeKeeperScreenController.userInfo();
                }
            }
        }
    }


    public static void cleanUp(String file, String tag) {
        FileWriter fw = null;
        PrintWriter pw = null;
        try {
            fw = new FileWriter
                    (String.format("src/com/og/XMLs/%s", file));
            pw = new PrintWriter(fw);
            pw.write(String.format("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                    "<%s>\n" +
                    "    \n" +
                    "</%s>", tag, tag));
            pw.close();
            fw.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
