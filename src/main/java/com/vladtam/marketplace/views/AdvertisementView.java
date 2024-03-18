package com.vladtam.marketplace.views;

import com.vladtam.marketplace.models.*;

import java.util.List;
import java.util.Scanner;

public class AdvertisementView implements BaseView{
    @Override
    public BaseModel createNew(Scanner scan) {
        Advertisement advertisement = new Advertisement();
        Category category = new Category();
        User user = new User();

        System.out.println("Input name: ");
        advertisement.setName(scan.nextLine());
        System.out.println("Input description: ");
        advertisement.setDescription(scan.nextLine());
        System.out.println("Input price: ");
        advertisement.setPrice(scan.nextDouble());
        advertisement.setSaleStatus(false);

        List<BaseModel> categoriesList = category.getListInfo();
        MainView.outputList(categoriesList);
        System.out.println("Select category (1-" + categoriesList.size() + "): ");
        advertisement.setCategory((Category) category.getFullInfo(scan.nextInt()));

        List<BaseModel> usersList = user.getListInfo();
        MainView.outputList(usersList);
        System.out.println("Select vendor (1-" + usersList.size() + "): ");
        advertisement.setVendor((User) user.getFullInfo(scan.nextInt()));
        scan.nextLine();

        return advertisement;
    }

    @Override
    public void outputPage(int id) {
        BaseModel bsModel = new Advertisement();
        System.out.println(bsModel.getFullInfo(id).outputFullInfo());
    }

    @Override
    public BaseModel updateModel(BaseModel bsModel, Scanner scan) {
        Advertisement advertisement = (Advertisement) bsModel;
        while(true) {
            System.out.println("Choice field to update:\n1-Product name\n2-Description\n3-Price\n" +
                    "4-Category\n5-Vendor\n'R'eturn\n");
            String choice = scan.nextLine();
            if (choice.equalsIgnoreCase("R")) {
                return advertisement;
            } else {
                try {
                    int index = Integer.parseInt(choice);
                    if (index >= 1 && index <= 5) {
                        switch (index) {
                            case 1:
                                System.out.println("Input product name: ");
                                advertisement.setName(scan.nextLine());
                                break;
                            case 2:
                                System.out.println("Input description: ");
                                advertisement.setDescription(scan.nextLine());
                                break;
                            case 3:
                                System.out.println("Input price: ");
                                advertisement.setPrice(scan.nextDouble());
                                scan.nextLine();
                                break;
                            case 4:
                                Category category = new Category();
                                List<BaseModel> categoriesList = category.getListInfo();
                                MainView.outputList(categoriesList);
                                System.out.println("Select category (1-" + categoriesList.size() + ") or create new(0): ");
                                int categoryChoice = scan.nextInt();
                                scan.nextLine();
                                if(categoryChoice == 0){
                                    advertisement.setCategory((Category) category.getFullInfo(category.createNew(scan)));
                                }else
                                    advertisement.setCategory((Category) category.getFullInfo(categoriesList.get(categoryChoice - 1).getId()));
                                break;
                            case 5:
                                User user = new User();
                                List<BaseModel> usersList = user.getListInfo();
                                MainView.outputList(usersList);
                                System.out.println("Select vendor (1-" + usersList.size() + ") or create new(0): ");
                                int vendorChoice = scan.nextInt();
                                scan.nextLine();
                                if(vendorChoice == 0){
                                    advertisement.setVendor((User) user.getFullInfo(user.createNew(scan)));
                                }else
                                    advertisement.setVendor((User) user.getFullInfo(usersList.get(vendorChoice - 1).getId()));
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
