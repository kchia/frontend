package com.gu.discussion.page

import com.gu.automation.support.Config
import com.gu.automation.support.page.Element._
import com.gu.discussion.support.ByExt
import org.openqa.selenium.{WebDriver}
import org.openqa.selenium.By._


case class UserProfilePage(implicit driver: WebDriver) {

  private def commentsTab = driver element ByExt.dataTypeStream("discussions")
  private def repliesTab = driver element ByExt.dataTypeStream("replies")
  private def featuredTab = driver element ByExt.dataTypeStream("picks")
  private def profileName = driver element className("user-profile__name")
  private def activityItemTitle = driver element className("user-profile__name")
  private def activityStreamEmpty = driver element className("user-profile__name")
  private def activitySearch = driver element id("activity-stream-search")

  def getUserProfileName = {
    val userProfileName = profileName.getText()
    userProfileName
  }

  def viewProfileComments(): UserProfilePage = {
    commentsTab.click()
    waitForUserHistoryToLoad()
    this
  }

  def viewProfileReplies(): UserProfilePage = {
    repliesTab.click()
    waitForUserHistoryToLoad()
    this
  }

  def viewProfileFeatured(): UserProfilePage = {
    featuredTab.click()
    waitForUserHistoryToLoad()
    this
  }

  def searchForComment(): UserProfilePage = {
    activitySearch.sendKeys("This is a test")
    activitySearch.click()
    waitForUserHistoryToLoad()
    this
  }

  def waitForUserHistoryToLoad() = {
    if (activityItemTitle.safeGet.map(_.isDisplayed) != Some(true)
      && activityStreamEmpty.safeGet.map(_.isDisplayed) != Some(true)) {
      throw new RuntimeException("Content not loaded!")
    }
    this
  }

}

object UserProfilePage {

  def goto()(implicit driver: WebDriver) = {
    driver.get(Config().getUserValue("identityURL") + "user/id/" + Config().getUserValue("testUserProfile"))
    UserProfilePage()
  }
}