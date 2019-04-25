package com.example.hp.mycampus.model;

public class Information {
    private String id;// 学号
    private String name;// 姓名
    private String sex;// 性别
    private String idnumber;// 身份证号
    private String birthday;// 出生年月
    private String nativeplace;// 籍贯
    private String departments;// 院系名称
    private String major;// 专业名称
    private String grade;// 年级
    private String ch_code;// 国家专业代码
    private String ch_name;// 国家专业名称
    private String year;// 学制
    private String type;// 学生类型
    private String state;// 学籍状态
    private String inSchool;// 是否在校
    private String candidateNumber;// 考生号
    private String change;// 异动
    private String pre_photo;// 入学照片
    private String in_photo;// 在校照片
    private String post_photo;// 毕业照片

    public Information(String id, String name, String sex, String idnumber, String birthday, String nativeplace,
                       String departments, String major, String grade, String ch_code, String ch_name, String year, String type,
                       String state, String inSchool, String candidateNumber, String change, String pre_photo, String in_photo,
                       String post_photo) {
        super();
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.idnumber = idnumber;
        this.birthday = birthday;
        this.nativeplace = nativeplace;
        this.departments = departments;
        this.major = major;
        this.grade = grade;
        this.ch_code = ch_code;
        this.ch_name = ch_name;
        this.year = year;
        this.type = type;
        this.state = state;
        this.inSchool = inSchool;
        this.candidateNumber = candidateNumber;
        this.change = change;
        this.pre_photo = pre_photo;
        this.in_photo = in_photo;
        this.post_photo = post_photo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getNativeplace() {
        return nativeplace;
    }

    public void setNativeplace(String nativeplace) {
        this.nativeplace = nativeplace;
    }

    public String getDepartments() {
        return departments;
    }

    public void setDepartments(String departments) {
        this.departments = departments;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getCh_code() {
        return ch_code;
    }

    public void setCh_code(String ch_code) {
        this.ch_code = ch_code;
    }

    public String getCh_name() {
        return ch_name;
    }

    public void setCh_name(String ch_name) {
        this.ch_name = ch_name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getInSchool() {
        return inSchool;
    }

    public void setInSchool(String inSchool) {
        this.inSchool = inSchool;
    }

    public String getCandidateNumber() {
        return candidateNumber;
    }

    public void setCandidateNumber(String candidateNumber) {
        this.candidateNumber = candidateNumber;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public String getPre_photo() {
        return pre_photo;
    }

    public void setPre_photo(String pre_photo) {
        this.pre_photo = pre_photo;
    }

    public String getIn_photo() {
        return in_photo;
    }

    public void setIn_photo(String in_photo) {
        this.in_photo = in_photo;
    }

    public String getPost_photo() {
        return post_photo;
    }

    public void setPost_photo(String post_photo) {
        this.post_photo = post_photo;
    }

    @Override
    public String toString() {
        return "Information [id=" + id + ", name=" + name + ", sex=" + sex + ", idnumber=" + idnumber + ", birthday="
                + birthday + ", nativeplace=" + nativeplace + ", departments=" + departments + ", major=" + major
                + ", grade=" + grade + ", ch_code=" + ch_code + ", ch_name=" + ch_name + ", year=" + year + ", type="
                + type + ", state=" + state + ", inSchool=" + inSchool + ", candidateNumber=" + candidateNumber
                + ", change=" + change + ", pre_photo=" + pre_photo + ", in_photo=" + in_photo + ", post_photo="
                + post_photo + "]";
    }

}

