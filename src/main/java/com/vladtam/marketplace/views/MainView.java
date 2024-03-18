package com.vladtam.marketplace.views;

import com.vladtam.marketplace.models.*;

import java.util.List;
import java.util.Scanner;

public class MainView {
    public static void mainMenu(){
        while (true) {
            System.out.flush();
            System.out.println("MAIN MENU\n1 - Address info\n2 - Advertisement info\n" +
                    "3 - Category info\n4 - City info\n5 - Review info\n6 - User info\n0 - Exit");
            Scanner scan = new Scanner(System.in);
            int choice = scan.nextInt();
            scan.nextLine();
            List<BaseModel> modelList = null;
            BaseModel bsModel = null;
            BaseView bsView = null;
            switch (choice){
                case 0:
                    System.exit(0);
                case 1:
                    bsModel = new Address();
                    bsView = new AddressView();
                    MainView.actionChoice(bsModel, bsView, scan);
                    break;
                case 2:
                    bsModel = new Advertisement();
                    bsView = new AdvertisementView();
                    MainView.actionChoice(bsModel, bsView, scan);
                    break;
                case 3:
                    bsModel = new Category();
                    bsView = new CategoryView();
                    MainView.actionChoice(bsModel, bsView, scan);
                    break;
                case 4:
                    bsModel = new City();
                    bsView = new CityView();
                    MainView.actionChoice(bsModel, bsView, scan);
                    break;
                case 5:
                    bsModel = new Review();
                    bsView = new ReviewView();
                    MainView.actionChoice(bsModel, bsView, scan);
                    break;
                case 6:
                    bsModel = new User();
                    bsView = new UserView();
                    MainView.actionChoice(bsModel, bsView, scan);
                    break;
            }
        }
    }

    public static void outputList(List<BaseModel> list){
        for (int i = 0; i < list.size(); i++) {
            System.out.println(i+1 + ") " + list.get(i));
        }
    }

    public static void actionChoice(BaseModel bsModel, BaseView bsView, Scanner scan){
        while (true) {
            List<BaseModel> modelList = bsModel.getListInfo();
            MainView.outputList(modelList);
            System.out.println("'1'-'" + modelList.size() + "' get full info\n'C'reate\n'R'eturn");
            String input = scan.nextLine();
            if (input.equalsIgnoreCase("R")) {
                return;
            } else if (input.equalsIgnoreCase("C")) {
                bsModel.createNew(scan);
            } else {
                try {
                    int index = Integer.parseInt(input);
                    if (index >= 1 && index <= modelList.size()) {
                        pageChoice(modelList.get(index - 1), bsView, scan);
                    } else throw new NumberFormatException();
                } catch (NumberFormatException e) {
                    System.out.println("Try again");
                }
            }
        }
    }

    public static void pageChoice(BaseModel bsModel, BaseView bsView, Scanner scan){
        int modelId = bsModel.getId();
        while(true) {
            bsView.outputPage(modelId);
            System.out.println("'U'pdate\n'D'elete\n'R'eturn\n");
            String input = scan.nextLine();
            if (input.equalsIgnoreCase("R")) {
                return;
            } else if (input.equalsIgnoreCase("D")) {
                bsModel.delete(modelId);
                return;
            } else if (input.equalsIgnoreCase("U")) {
                bsModel.update(bsModel.getFullInfo(bsModel.getId()), scan);
            } else
                return;
        }
    }
}
