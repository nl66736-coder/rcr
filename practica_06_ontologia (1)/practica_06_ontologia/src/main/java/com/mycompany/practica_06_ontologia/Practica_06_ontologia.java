/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.practica_06_ontologia;

import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.ModelFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionRemote;

/**
 *
 * @author sflanza
 */
public class Practica_06_ontologia {

    private static String PATH = "felinos.rdf";
    private static String URL_FUSEKI = "http://localhost:3030/felinos";

    public static void main(String[] args) {

        try {
            int opcion = Integer.parseInt(args[0]);

            System.out.println("****************************** C O M I E N Z O ******************************");
            System.out.println("Opción: " + opcion);
            System.out.println();

            if (opcion == 1) {
                crearExportarOntologia();
            }
            if (opcion == 2) {
                importarMostrarOntologia();
            }
            if (opcion == 3) {
                consultarOntologiaDeFuseki();
            }
            System.out.println("****************************** F I N A L I Z A C I Ó N ******************************");
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Debe introducir parámetro 1, 2 o 3.");
        }
    }

    private static void crearExportarOntologia() {
        OntModel modelo = ModelFactory.createOntologyModel();

        OntClass felino = modelo.createClass(URL_FUSEKI + "Felino");
        OntClass felinoDomestico = modelo.createClass(URL_FUSEKI + "FelinoDomestico");
        OntClass felinoSalvaje = modelo.createClass(URL_FUSEKI + "FelinoSalvaje");
        OntClass casa = modelo.createClass(URL_FUSEKI + "Casa");
        OntClass callejero = modelo.createClass(URL_FUSEKI + "Callejero");
        OntClass leon = modelo.createClass(URL_FUSEKI + "León");
        OntClass tigre = modelo.createClass(URL_FUSEKI + "Tigre");
        OntClass bengala = modelo.createClass(URL_FUSEKI + "Bengala");
        OntClass siberiano = modelo.createClass(URL_FUSEKI + "Siberiano");
        OntClass sumatra = modelo.createClass(URL_FUSEKI + "Sumatra");
        OntClass leopardo = modelo.createClass(URL_FUSEKI + "Leopardo");
        OntClass sphynxClass = modelo.createClass(URL_FUSEKI + "Sphynx");
        OntClass siamesClass = modelo.createClass(URL_FUSEKI + "Siames");
        OntClass himalayoClass = modelo.createClass(URL_FUSEKI + "Siames");
        OntClass persaClass = modelo.createClass(URL_FUSEKI + "Siames");

        felino.addSubClass(felinoDomestico);
        felino.addSubClass(felinoSalvaje);
        felinoDomestico.addDisjointWith(felinoSalvaje);
        felinoSalvaje.addDisjointWith(felinoDomestico);

        felinoDomestico.addSubClass(casa);
        felinoDomestico.addSubClass(callejero);
        casa.addDisjointWith(callejero);
        callejero.addDisjointWith(casa);
        sphynxClass.addDisjointWith(siamesClass);
        sphynxClass.addDisjointWith(himalayoClass);
        sphynxClass.addDisjointWith(persaClass);
        siamesClass.addDisjointWith(sphynxClass);
        siamesClass.addDisjointWith(himalayoClass);
        siamesClass.addDisjointWith(persaClass);
        himalayoClass.addDisjointWith(sphynxClass);
        himalayoClass.addDisjointWith(siamesClass);
        himalayoClass.addDisjointWith(persaClass);
        persaClass.addDisjointWith(sphynxClass);
        persaClass.addDisjointWith(himalayoClass);
        persaClass.addDisjointWith(siamesClass);

        felinoSalvaje.addSubClass(leon);
        felinoSalvaje.addSubClass(tigre);
        felinoSalvaje.addSubClass(leopardo);
        tigre.addSubClass(bengala);
        tigre.addSubClass(siberiano);
        tigre.addSubClass(sumatra);
        leon.addDisjointWith(tigre);
        leon.addDisjointWith(leopardo);
        tigre.addDisjointWith(leon);
        tigre.addDisjointWith(leopardo);
        leopardo.addDisjointWith(leon);
        leopardo.addDisjointWith(tigre);

        DatatypeProperty domestico = modelo.createDatatypeProperty(URL_FUSEKI + "domestico");
        domestico.addDomain(felinoDomestico);
        domestico.addDomain(felinoSalvaje);

        DatatypeProperty agresivo = modelo.createDatatypeProperty(URL_FUSEKI + "agresivo");
        agresivo.addDomain(felinoDomestico);
        agresivo.addDomain(felinoSalvaje);

        DatatypeProperty carino = modelo.createDatatypeProperty(URL_FUSEKI + "cariñoso");
        carino.addDomain(casa);
        carino.addDomain(callejero);

        DatatypeProperty raza = modelo.createDatatypeProperty(URL_FUSEKI + "raza");
        raza.addDomain(sphynxClass);
        raza.addDomain(himalayoClass);
        raza.addDomain(siamesClass);
        raza.addDomain(persaClass);

        DatatypeProperty melena = modelo.createDatatypeProperty(URL_FUSEKI + "melena");
        melena.addDomain(leon);
        melena.addDomain(tigre);
        melena.addDomain(leopardo);

        DatatypeProperty bandas = modelo.createDatatypeProperty(URL_FUSEKI + "bandas");
        bandas.addDomain(leon);
        bandas.addDomain(tigre);
        bandas.addDomain(leopardo);

        DatatypeProperty localizacion = modelo.createDatatypeProperty(URL_FUSEKI + "localización");
        localizacion.addDomain(bengala);
        localizacion.addDomain(siberiano);
        localizacion.addDomain(sumatra);

        DatatypeProperty tamano = modelo.createDatatypeProperty(URL_FUSEKI + "tamaño");
        tamano.addDomain(bengala);
        tamano.addDomain(siberiano);
        tamano.addDomain(sumatra);

        Individual siames = modelo.createIndividual(URL_FUSEKI + "Siames", siamesClass);
        Individual persa = modelo.createIndividual(URL_FUSEKI + "Persa", persaClass);
        Individual himalayo = modelo.createIndividual(URL_FUSEKI + "Himalayo", himalayoClass);
        Individual sphynx = modelo.createIndividual(URL_FUSEKI + "Sphynx", sphynxClass);
        Individual gato = modelo.createIndividual(URL_FUSEKI + "Gato", callejero);
        Individual leo = modelo.createIndividual(URL_FUSEKI + "Leon", leon);
        Individual pantheraBengala = modelo.createIndividual(URL_FUSEKI + "Bengala", bengala);
        Individual pantheraSiberiano = modelo.createIndividual(URL_FUSEKI + "Siberiano", siberiano);
        Individual pantheraSumatra = modelo.createIndividual(URL_FUSEKI + "Sumatra", sumatra);
        Individual pardo = modelo.createIndividual(URL_FUSEKI + "Leopardo", leopardo);

        siames.addLiteral(domestico, URL_FUSEKI + true);
        siames.addLiteral(agresivo, URL_FUSEKI + false);
        siames.addLiteral(raza, URL_FUSEKI + "Siames");
        siames.addLiteral(carino, URL_FUSEKI + true);
        persa.addLiteral(domestico, URL_FUSEKI + true);
        persa.addLiteral(agresivo, URL_FUSEKI + false);
        persa.addLiteral(raza, URL_FUSEKI + "Persa");
        persa.addLiteral(carino, URL_FUSEKI + true);
        himalayo.addLiteral(domestico, URL_FUSEKI + true);
        himalayo.addLiteral(agresivo, URL_FUSEKI + false);
        himalayo.addLiteral(raza, URL_FUSEKI + "himalayo");
        himalayo.addLiteral(carino, URL_FUSEKI + true);
        sphynx.addLiteral(domestico, URL_FUSEKI + true);
        sphynx.addLiteral(agresivo, URL_FUSEKI + false);
        sphynx.addLiteral(raza, URL_FUSEKI + "Sphynx");
        sphynx.addLiteral(carino, URL_FUSEKI + true);
        gato.addLiteral(domestico, URL_FUSEKI + true);
        gato.addLiteral(agresivo, URL_FUSEKI + false);
        gato.addLiteral(carino, URL_FUSEKI + false);
        leo.addLiteral(domestico, URL_FUSEKI + false);
        leo.addLiteral(agresivo, URL_FUSEKI + true);
        leo.addLiteral(melena, URL_FUSEKI + true);
        leo.addLiteral(bandas, URL_FUSEKI + "No tiene");
        pantheraBengala.addLiteral(domestico, URL_FUSEKI + false);
        pantheraBengala.addLiteral(agresivo, URL_FUSEKI + true);
        pantheraBengala.addLiteral(melena, URL_FUSEKI + false);
        pantheraBengala.addLiteral(bandas, URL_FUSEKI + "Rayas");
        pantheraBengala.addLiteral(localizacion, URL_FUSEKI + "India");
        pantheraBengala.addLiteral(tamano, URL_FUSEKI + "Normal");
        pantheraSiberiano.addLiteral(domestico, URL_FUSEKI + false);
        pantheraSiberiano.addLiteral(agresivo, URL_FUSEKI + true);
        pantheraSiberiano.addLiteral(melena, URL_FUSEKI + false);
        pantheraSiberiano.addLiteral(bandas, URL_FUSEKI + "Rayas");
        pantheraSiberiano.addLiteral(localizacion, URL_FUSEKI + "China");
        pantheraSiberiano.addLiteral(tamano, URL_FUSEKI + "Grande");
        pantheraSumatra.addLiteral(domestico, URL_FUSEKI + false);
        pantheraSumatra.addLiteral(agresivo, URL_FUSEKI + true);
        pantheraSumatra.addLiteral(melena, URL_FUSEKI + false);
        pantheraSumatra.addLiteral(bandas, URL_FUSEKI + "Rayas");
        pantheraSumatra.addLiteral(localizacion, URL_FUSEKI + "Indonesia");
        pantheraSumatra.addLiteral(tamano, URL_FUSEKI + "Pequeño");
        pardo.addLiteral(domestico, URL_FUSEKI + false);
        pardo.addLiteral(agresivo, URL_FUSEKI + true);
        pardo.addLiteral(melena, URL_FUSEKI + false);
        pardo.addLiteral(bandas, URL_FUSEKI + "Puntos");

        modelo.write(System.out, "RDF/XML");

        try {
            OutputStream out = new FileOutputStream(PATH);
            modelo.write(out, "RDF/XML");
            out.close();
            System.out.println("Exportado a: " + PATH);
        } catch (IOException e) {
            System.err.println("Error al exportar: " + e.getMessage());
        }
    }

    private static void importarMostrarOntologia() {
        OntModel modelo = ModelFactory.createOntologyModel();

        modelo.read(PATH);

        StmtIterator iterator = modelo.listStatements();
        while (iterator.hasNext()) {
            Statement statement = iterator.next();
            System.out.println(statement);
        }
    }

    private static void consultarOntologiaDeFuseki() {
        OntModel modelo = ModelFactory.createOntologyModel();

        modelo.read(PATH);

        RDFConnection conn = RDFConnectionRemote.newBuilder().destination(URL_FUSEKI).build();

        Query query = QueryFactory.create("SELECT * { ?sujeto ?predicado ?objeto }");

        conn.queryResultSet(query, ResultSetFormatter::out);
    }
}