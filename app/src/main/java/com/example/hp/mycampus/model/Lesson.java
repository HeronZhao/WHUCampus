package com.example.hp.mycampus.model;

import java.io.Serializable;


public class Lesson implements Serializable{
    private String lessonName;// 课程名
    private String day;// 哪一天
    private String beginWeek;// 上课时间，从第几周开始
    private String endWeek;// 上课时间，到第几周结束
    private String classNote;
    private String beginTime;// 上课时间，从第几节课开始
    private String endTime;// 上课时间，到第几节课结束
    private String detail;// 课程的详细信息
    private String classRoom;// 教室
    private String weekInterVal;// 隔几周一次
    private String teacherName;// 任课老师
    private String professionName;// 教师职称
    private String planType;
    private String credit;// 课程学分
    private String areaName;
    private String academicTeach;
    private String grade;
    private String note;
    private String state;

    public Lesson(String lessonName, String day, String beginWeek, String endWeek, String classNote, String beginTime,
                  String endTime, String detail, String classRoom, String weekInterVal, String teacherName,
                  String professionName, String planType, String credit, String areaName, String academicTeach, String grade,
                  String note, String state) {
        super();
        this.lessonName = lessonName;
        this.day = day;
        this.beginWeek = beginWeek;
        this.endWeek = endWeek;
        this.classNote = classNote;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.detail = detail;
        this.classRoom = classRoom;
        this.weekInterVal = weekInterVal;
        this.teacherName = teacherName;
        this.professionName = professionName;
        this.planType = planType;
        this.credit = credit;
        this.areaName = areaName;
        this.academicTeach = academicTeach;
        this.grade = grade;
        this.note = note;
        this.state = state;
    }

    public Lesson(String lessonName, String teacherName, String classRoom, String day, String beginTime, String endTime) {
        this.lessonName=lessonName;
        this.teacherName=teacherName;
        this.classRoom=classRoom;
        this.day=day;
        this.beginTime=beginTime;
        this.endTime=endTime;
    }


    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getBeginWeek() {
        return beginWeek;
    }

    public void setBeginWeek(String beginWeek) {
        this.beginWeek = beginWeek;
    }

    public String getEndWeek() {
        return endWeek;
    }

    public void setEndWeek(String endWeek) {
        this.endWeek = endWeek;
    }

    public String getClassNote() {
        return classNote;
    }

    public void setClassNote(String classNote) {
        this.classNote = classNote;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(String classRoom) {
        this.classRoom = classRoom;
    }

    public String getWeekInterVal() {
        return weekInterVal;
    }

    public void setWeekInterVal(String weekInterVal) {
        this.weekInterVal = weekInterVal;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getProfessionName() {
        return professionName;
    }

    public void setProfessionName(String professionName) {
        this.professionName = professionName;
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAcademicTeach() {
        return academicTeach;
    }

    public void setAcademicTeach(String academicTeach) {
        this.academicTeach = academicTeach;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Lesson [lessonName=" + lessonName + ", day=" + day + ", beginWeek=" + beginWeek + ", endWeek=" + endWeek
                + ", classNote=" + classNote + ", beginTime=" + beginTime + ", endTime=" + endTime + ", detail="
                + detail + ", classRoom=" + classRoom + ", weekInterVal=" + weekInterVal + ", teacherName="
                + teacherName + ", professionName=" + professionName + ", planType=" + planType + ", credit=" + credit
                + ", areaName=" + areaName + ", academicTeach=" + academicTeach + ", grade=" + grade + ", note=" + note
                + ", state=" + state + "]";
    }

}

