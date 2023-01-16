package com.example.chatapp;

import java.util.List;

public class GroupModels {
    String groupName, groupExplanation, groupImage, groupId;
    List<String> numbers;

    public GroupModels(String groupName, String groupExplanation, String groupImage,List<String> numbers,  String groupId){
        this.groupName = groupName;
        this.groupExplanation = groupExplanation;
        this.groupImage = groupImage;
        this.groupId = groupId;
        this.numbers = numbers;
    }

    public String getGroupName() {
        return groupName;
    }
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    public String getGroupExplanation() {
        return groupName;
    }
    public void setGroupExplanation(String groupExplanation) {
        this.groupExplanation = groupExplanation;
    }
    public String getGroupImage() {
        return groupImage;
    }
    public void setGroupImage(String groupImage) {
        this.groupImage = groupImage;
    }
    public String getGroupId() {
        return groupId;
    }
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
    public List<String> getNumbers(){
        return  numbers;
    }
    public void setNumbers(List<String> numbers){
        this.numbers = numbers;
    }
}
