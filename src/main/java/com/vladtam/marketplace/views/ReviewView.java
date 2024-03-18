package com.vladtam.marketplace.views;

import com.vladtam.marketplace.models.*;

import java.util.List;
import java.util.Scanner;

public class ReviewView implements BaseView{
    @Override
    public BaseModel createNew(Scanner scan) {
        Review review = new Review();
        User user = new User();
        System.out.println("Input rating (1-10): ");
        review.setRate(scan.nextByte());
        scan.nextLine();
        System.out.println("Input comment: ");
        review.setComment(scan.nextLine());
        List<BaseModel> usersList = user.getListInfo();
        MainView.outputList(usersList);
        System.out.println("Select sender (1-" + usersList.size() + "): ");
        review.setSender((User) user.getFullInfo(scan.nextInt()));
        System.out.println("Select receiver (1-" + usersList.size() + "): ");
        review.setReceiver((User) user.getFullInfo(scan.nextInt()));
        scan.nextLine();
        return review;
    }

    @Override
    public void outputPage(int id) {
        BaseModel bsModel = new Review();
        System.out.println(bsModel.getFullInfo(id).outputFullInfo());
    }

    @Override
    public BaseModel updateModel(BaseModel bsModel, Scanner scan) {
        Review review = (Review) bsModel;
        while(true) {
            System.out.println("Choice field to update:\n1-Rating\n2-Comment\n3-Sender\n4-Receiver\n'R'eturn\n");
            String choice = scan.nextLine();
            if (choice.equalsIgnoreCase("R")) {
                return review;
            } else {
                try {
                    int index = Integer.parseInt(choice);
                    if (index >= 1 && index <= 4) {
                        switch (index) {
                            case 1:
                                System.out.println("Input rating: ");
                                review.setRate(scan.nextByte());
                                scan.nextLine();
                                break;
                            case 2:
                                System.out.println("Input comment: ");
                                review.setComment(scan.nextLine());
                                break;
                            case 3:
                                User sender = new User();
                                List<BaseModel> sendersList = sender.getListInfo();
                                MainView.outputList(sendersList);
                                System.out.println("Select sender (1-" + sendersList.size() + ") or create new(0): ");
                                int senderChoice = scan.nextInt();
                                scan.nextLine();
                                if(senderChoice == 0){
                                    review.setSender((User) sender.getFullInfo(sender.createNew(scan)));
                                }else
                                    review.setSender((User) sender.getFullInfo(sendersList.get(senderChoice - 1).getId()));
                                break;
                            case 4:
                                User receiver = new User();
                                List<BaseModel> receiversList = receiver.getListInfo();
                                MainView.outputList(receiversList);
                                System.out.println("Select receiver (1-" + receiversList.size() + ") or create new(0): ");
                                int receiverChoice = scan.nextInt();
                                scan.nextLine();
                                if(receiverChoice == 0){
                                    review.setReceiver((User) receiver.getFullInfo(receiver.createNew(scan)));
                                }else
                                    review.setReceiver((User) receiver.getFullInfo(receiversList.get(receiverChoice - 1).getId()));
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
