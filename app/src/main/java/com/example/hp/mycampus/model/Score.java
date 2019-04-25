package com.example.hp.mycampus.model;

import java.io.Serializable;

public class Score implements Serializable {


	private String year;// 学年
	private String semester;// 学期
	private String name;// 课程名称
	private String credit;// 学分
	private String score;// 成绩


	public Score(String year, String semester, String name, String credit, String score) {
		this.year=year;
		this.semester=semester;
		this.name=name;
		this.credit=credit;
		this.score=score;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getCredit() {
		return credit;
	}

	public void setCredit(String credit) {
		this.credit = credit;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "Score [ name=" + name  + ", credit=" +  credit + ", year=" + year + ", semester=" + semester + ", score=" + score + "]";
	}
}
