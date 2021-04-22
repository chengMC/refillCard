package com.mc.refillCard.util;

import net.sf.json.xml.XMLSerializer;

public class XmlUtils {

    public static String xml2json(String xml) {
        //创建XMLSerializer对象
        XMLSerializer xmlSerializer = new XMLSerializer();
        //将xml转化为json
        String result = xmlSerializer.read(xml).toString();
        System.out.println(result);
        return result;
    }

//    public static String json2xml(String json) {
//        StringReader input = new StringReader(json);
//        StringWriter output = new StringWriter();
//        JsonXMLConfig config = new JsonXMLConfigBuilder().multiplePI(false).repairingNamespaces(false).build();
//        try {
//            XMLEventReader reader = new JsonXMLInputFactory(config).createXMLEventReader(input);
//            XMLEventWriter writer = XMLOutputFactory.newInstance().createXMLEventWriter(output);
//            writer = new PrettyXMLEventWriter(writer);
//            writer.add(reader);
//            reader.close();
//            writer.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                output.close();
//                input.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        if (output.toString().length() >= 38) {// remove <?xml version="1.0" encoding="UTF-8"?>
//            System.out.println(output.toString().substring(39));
//            return output.toString().substring(39);
//        }
//        return output.toString();
//    }


}
