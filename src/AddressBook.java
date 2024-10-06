

import java.util.HashMap;
import java.util.Scanner;
import java.io.*;

public class AddressBook {

    private HashMap<String, String> contacts;

    public AddressBook() {
        contacts = new HashMap<>();
    }

    // Metodo para cargar los contactos
    public void load(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                contacts.put(parts[0], parts[1]);  // Numero, Nombre
            }
            System.out.println("Contactos Cargados Correctamente!\n");
        } catch (IOException e) {
            System.out.println("No se pudo Cargar la Lista :( - " + e.getMessage());
        }
    }

    // Metodo para guardar los contactos
    public void save(String fileName) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (String number : contacts.keySet()) {
                bw.write(number + "," + contacts.get(number));
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("No se Pudo Actualizar la Lista :( - " + e.getMessage());
        }
    }

    // Mostrar contactos
    public void list() {
        if (contacts.isEmpty()) {
            System.out.println("Aun NO Hay Contactos Registrados!");
        } else {
            for (String number : contacts.keySet()) {
                System.out.println(number + " : " + contacts.get(number));
            }
        }
    }

    // Añadir un contacto
    public void create(String number, String name, String fileName) {
        contacts.put(number, name);
        System.out.println("Se ha Agregado un Contacto! " + number);
        save(fileName);
    }

    // Borrar un contacto
    public void delete(String number, String fileName) {
        if (contacts.containsKey(number)) {
            contacts.remove(number);
            System.out.println("Se ha Eliminado un Contacto!");
            save(fileName);
        } else {
            System.out.println("Este Numero no Esta Registrado!");
        }
    }

    // Editar un contacto
    public void editContact(String number, String fileName) {
        if (contacts.containsKey(number)) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Ingrese el Nuevo Nombre: ");
            String newName = scanner.nextLine();
            System.out.print("Desea Cambiar el Numero? (S/N): ");
            String changeNumber = scanner.nextLine();
            if (changeNumber.equalsIgnoreCase("S")) {
                System.out.print("Ingrese el Nuevo Numero: ");
                String newNumber = scanner.nextLine();
                contacts.remove(number);
                contacts.put(newNumber, newName);
                System.out.println("Contacto Actualizado Correctamente!");
            } else {
                contacts.put(number, newName);
                System.out.println("Nombre Actualizado Correctamente!");
            }
            save(fileName);
        } else {
            System.out.println("Este Numero no Esta Registrado!");
        }
    }

    // Buscar un numero por nombre
    public void searchByName(String name) {
        boolean found = false;
        for (String number : contacts.keySet()) {
            if (contacts.get(number).equalsIgnoreCase(name)) {
                System.out.println("Contacto: " + name + " Numero: " + number);
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Ese Contacto no Esta Registrado!");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AddressBook addressBook = new AddressBook();
        String fileName = "Arch/Output.csv";

        System.out.println("Hecho por Maximiliano GM");
        System.out.println("*************************\n");

        // Cargar contactos
        addressBook.load("Arch/Input.csv");

        // Menu
        while (true) {
            System.out.println("\nAdministrar Contactos");
            System.out.println("1. Lista de Contactos");
            System.out.println("2. Agregar un Contacto");
            System.out.println("3. Borrar un Contacto");
            System.out.println("4. Buscar Numero de un Contacto");
            System.out.println("5. Editar un Contacto");
            System.out.println("6. Salir");
            System.out.print("Por Favor Elige Una Opcion: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    addressBook.list();
                    break;
                case 2:
                    System.out.print("Nombre: ");
                    String name = scanner.nextLine();
                    System.out.print("Numero: ");
                    String number = scanner.nextLine();
                    addressBook.create(number, name, fileName);
                    break;
                case 3:
                    System.out.print("Numero del Contacto para Eliminar: ");
                    String delNumber = scanner.nextLine();
                    addressBook.delete(delNumber, fileName);
                    break;
                case 4:
                    System.out.print("Nombre del Contacto: ");
                    String searchName = scanner.nextLine();
                    addressBook.searchByName(searchName);
                    break;
                case 5:
                    System.out.print("Numero del Contacto para Editar: ");
                    String editNumber = scanner.nextLine();
                    addressBook.editContact(editNumber, fileName);
                    break;
                case 6:
                    System.out.println("Hasta Pronto!");
                    return;
                default:
                    System.out.println("Por Favor Seleccione Una Opcion del MENU:");
            }
        }
    }
}
