package com.prashant1;



import com.prashant1.Model.Book;
import com.prashant1.Model.Member;

import com.prashant1.operarion.BookOperation;
import com.prashant1.operarion.MemberOperation;
import com.prashant1.operarion.IssueBookOperation;
import com.prashant1.operarion.ReturnBookOperation;
import com.prashant1.operarion.DisplayOperation;

import java.util.Scanner;

public class Main {

    private static final Scanner sc = new Scanner(System.in);

    private static final BookOperation bookOperation =
            new BookOperation();

    private static final MemberOperation memberOperation =
            new MemberOperation();

    private static final IssueBookOperation issueOperation =
            new IssueBookOperation();

    private static final ReturnBookOperation returnOperation =
            new ReturnBookOperation();

    private static final DisplayOperation displayOperation =
            new DisplayOperation();

    public static void main(String[] args) {

        while (true) {

            System.out.println("\n====================================");
            System.out.println("   LIBRARY MANAGEMENT SYSTEM");
            System.out.println("====================================");

            System.out.println("1. Book Operations");
            System.out.println("2. Member Operations");
            System.out.println("3. Issue Book");
            System.out.println("4. Return Book");
            System.out.println("5. Display Issued Books");
            System.out.println("6. Display Available Books");
            System.out.println("7. Display Returned Books");
            System.out.println("0. Exit");

            int choice = readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    bookMenu();
                    break;

                case 2:
                    memberMenu();
                    break;

                case 3: {
                    int bookId = readInt("Enter Book ID: ");
                    int memberId = readInt("Enter Member ID: ");

                    issueOperation.issueBook(bookId, memberId);
                    break;
                }

                case 4: {
                    int issueId = readInt("Enter Issue ID: ");

                    returnOperation.returnBook(issueId);
                    break;
                }

                case 5:
                    displayOperation.displayIssuedBooks();
                    break;

                case 6:
                    displayOperation.displayAvailableBooks();
                    break;

                case 7:
                    displayOperation.displayReturnedBooks();
                    break;

                case 0:
                    System.out.println("Thank you for using the system.");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    // Book Menu
    private static void bookMenu() {

        while (true) {

            System.out.println("\n========== BOOK OPERATIONS ==========");
            System.out.println("1. Add Book");
            System.out.println("2. Display Books");
            System.out.println("3. Search Book");
            System.out.println("4. Update Book");
            System.out.println("5. Delete Book");
            System.out.println("0. Back to Main Menu");

            int choice = readInt("Enter your choice: ");

            switch (choice) {

                case 1: {
                    System.out.print("Enter Book Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Author Name: ");
                    String author = sc.nextLine();

                    System.out.print("Enter Publisher: ");
                    String publisher = sc.nextLine();

                    double price = readDouble("Enter Price: ");
                    int quantity = readInt("Enter Quantity: ");

                    if (price < 0 || quantity < 0) {
                        System.out.println(
                                "Price and quantity cannot be negative."
                        );
                        break;
                    }

                    Book book = new Book(
                            name, author, publisher, price, quantity
                    );

                    bookOperation.addBook(book);
                    break;
                }

                case 2:
                    bookOperation.displayBooks();
                    break;

                case 3: {
                    int id = readInt("Enter Book ID: ");
                    bookOperation.searchBook(id);
                    break;
                }

                case 4: {
                    int id = readInt("Enter Book ID to update: ");

                    System.out.print("Enter New Book Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter New Author Name: ");
                    String author = sc.nextLine();

                    System.out.print("Enter New Publisher: ");
                    String publisher = sc.nextLine();

                    double price = readDouble("Enter New Price: ");
                    int quantity = readInt("Enter New Quantity: ");

                    if (price < 0 || quantity < 0) {
                        System.out.println(
                                "Price and quantity cannot be negative."
                        );
                        break;
                    }

                    Book book = new Book(
                            name, author, publisher, price, quantity
                    );

                    book.setBookId(id);

                    bookOperation.updateBook(book);
                    break;
                }

                case 5: {
                    int id = readInt("Enter Book ID to delete: ");
                    bookOperation.deleteBook(id);
                    break;
                }

                case 0:
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    // Member Menu
    private static void memberMenu() {

        while (true) {

            System.out.println("\n========== MEMBER OPERATIONS ==========");
            System.out.println("1. Add Member");
            System.out.println("2. Display Members");
            System.out.println("3. Search Member");
            System.out.println("4. Update Member");
            System.out.println("5. Delete Member");
            System.out.println("0. Back to Main Menu");

            int choice = readInt("Enter your choice: ");

            switch (choice) {

                case 1: {
                    System.out.print("Enter Member Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Email: ");
                    String email = sc.nextLine();

                    System.out.print("Enter Mobile: ");
                    String mobile = sc.nextLine();

                    System.out.print("Enter Address: ");
                    String address = sc.nextLine();

                    Member member = new Member(
                            name, email, mobile, address
                    );

                    memberOperation.addMember(member);
                    break;
                }

                case 2:
                    memberOperation.displayMembers();
                    break;

                case 3: {
                    int id = readInt("Enter Member ID: ");
                    memberOperation.searchMember(id);
                    break;
                }

                case 4: {
                    int id = readInt("Enter Member ID to update: ");

                    System.out.print("Enter New Member Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter New Email: ");
                    String email = sc.nextLine();

                    System.out.print("Enter New Mobile: ");
                    String mobile = sc.nextLine();

                    System.out.print("Enter New Address: ");
                    String address = sc.nextLine();

                    Member member = new Member(
                            name, email, mobile, address
                    );

                    member.setMemberId(id);

                    memberOperation.updateMember(member);
                    break;
                }

                case 5: {
                    int id = readInt("Enter Member ID to delete: ");
                    memberOperation.deleteMember(id);
                    break;
                }

                case 0:
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    // Read integer safely
    private static int readInt(String message) {

        while (true) {

            System.out.print(message);
            String input = sc.nextLine().trim();

            try {
                return Integer.parseInt(input);

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    // Read decimal safely
    private static double readDouble(String message) {

        while (true) {

            System.out.print(message);
            String input = sc.nextLine().trim();

            try {
                return Double.parseDouble(input);

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}