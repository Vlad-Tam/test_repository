package com.vladtam.marketplace.views;

import com.vladtam.marketplace.models.*;

import java.util.List;
import java.util.Scanner;

public class UserView implements BaseView{
    @Override
    public BaseModel createNew(Scanner scan) {
        User user = new User();
        Address address = new Address();
        System.out.println("Input name: ");
        user.setName(scan.nextLine());
        System.out.println("Input surname: ");
        user.setSurname(scan.nextLine());
        System.out.println("Input email: ");
        user.setEmail(scan.nextLine());
        System.out.println("Input phone number: ");
        user.setPhoneNumber(scan.nextLine());
        System.out.println("Input password: ");
        user.setPassword(scan.nextLine());

        List<BaseModel> addressesList = address.getListInfo();
        MainView.outputList(addressesList);
        System.out.println("Select address (1-" + addressesList.size() + ") or create new(0): ");
        int choice = scan.nextInt();
        if(choice == 0){
            user.setAddress((Address) address.getFullInfo(address.createNew(scan)));
        }else
            user.setAddress((Address) address.getFullInfo(addressesList.get(choice - 1).getId()));
        scan.nextLine();
        return user;
    }

    @Override
    public void outputPage(int id) {
        BaseModel bsModel = new User();
        System.out.println(bsModel.getFullInfo(id).outputFullInfo());
    }

    @Override
    public BaseModel updateModel(BaseModel bsModel, Scanner scan) {
        User user = (User) bsModel;
        while(true) {
            System.out.println("Choice field to update:\n1-User name\n2-Surname\n3-Email\n4-Phone number\n" +
                    "5-Password\n6-Address\n'R'eturn\n");
            String choice = scan.nextLine();
            if (choice.equalsIgnoreCase("R")) {
                return user;
            } else {
                try {
                    int index = Integer.parseInt(choice);
                    if (index >= 1 && index <= 6) {
                        switch (index) {
                            case 1:
                                System.out.println("Input name: ");
                                user.setName(scan.nextLine());
                                break;
                            case 2:
                                System.out.println("Input surname: ");
                                user.setSurname(scan.nextLine());
                                break;
                            case 3:
                                System.out.println("Input email: ");
                                user.setEmail(scan.nextLine());
                                break;
                            case 4:
                                System.out.println("Input phone number: ");
                                user.setPhoneNumber(scan.nextLine());
                                break;
                            case 5:
                                System.out.println("Input password: ");
                                user.setPassword(scan.nextLine());
                                break;
                            case 6:
                                Address address = new Address();
                                List<BaseModel> addressesList = address.getListInfo();
                                MainView.outputList(addressesList);
                                System.out.println("Select address (1-" + addressesList.size() + ") or create new(0): ");
                                int addressChoice = scan.nextInt();
                                scan.nextLine();
                                if(addressChoice == 0){
                                    user.setAddress((Address) address.getFullInfo(address.createNew(scan)));
                                }else
                                    user.setAddress((Address) address.getFullInfo(addressesList.get(addressChoice - 1).getId()));
                                break;
                        }
                    } else throw new NumberFormatException();
                } catch (NumberFormatException e) {
                    System.out.println("Try again");
                }
            }
        }
    }
}