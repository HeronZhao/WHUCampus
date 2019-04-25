package com.example.hp.mycampus.model;

public class ChooseLessonItem {
    private String lessonName;// 课程名
    private String credit;// 学分
    private String remaining;// 剩余人数
    private String max;// 最大人数
    private String teacherName;// 任课老师
    private String professionName;// 教师职称
    private String place;// 授课学院
    private String textbook;// 教材
    private String year;// 学年
    private String semester;// 学期
    private String timeAndPlace;// 上课时间地点
    private String note;// 备注

    public ChooseLessonItem(String lessonName, String credit, String remaining, String max, String teacherName,
                            String professionName, String place, String textbook, String year, String semester, String timeAndPlace,
                            String note) {
        super();
        this.lessonName = lessonName;
        this.credit = credit;
        this.remaining = remaining;
        this.max = max;
        this.teacherName = teacherName;
        this.professionName = professionName;
        this.place = place;
        this.textbook = textbook;
        this.year = year;
        this.semester = semester;
        this.timeAndPlace = timeAndPlace;
        this.note = note;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getRemaining() {
        return remaining;
    }

    public void setRemaining(String remaining) {
        this.remaining = remaining;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
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

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getTextbook() {
        return textbook;
    }

    public void setTextbook(String textbook) {
        this.textbook = textbook;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getTimeAndPlace() {
        return timeAndPlace;
    }

    public void setTimeAndPlace(String timeAndPlace) {
        this.timeAndPlace = timeAndPlace;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "ChooseLessonItem [lessonName=" + lessonName + ", credit=" + credit + ", remaining=" + remaining
                + ", max=" + max + ", teacherName=" + teacherName + ", professionName=" + professionName + ", place="
                + place + ", textbook=" + textbook + ", year=" + year + ", semester=" + semester + ", timeAndPlace=<"
                + timeAndPlace + ">, note=<" + note + ">]";
    }
}

