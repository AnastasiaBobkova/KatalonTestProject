import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable

def id1 = UUID.randomUUID().toString()

def methodName1 = 'MyFunction1'

def name1 = 'testname1'

WS.sendRequestAndVerify(findTestObject('Background Tasks/Create/Create Background Task', [('id') : id1, ('name') : name1
            , ('methodName') : methodName1]))

def id2 = UUID.randomUUID().toString()

def methodName2 = 'MyFunction2'

def name2 = 'testname2'

WS.sendRequestAndVerify(findTestObject('Background Tasks/Create/Create Background Task', [('id') : id2, ('name') : name2
            , ('methodName') : methodName2]))

def filter = "?Filters=name==$name1"

def response = WS.sendRequestAndVerify(findTestObject('Background Tasks/GET/Get Background Task', [('queryString') : filter]))

WS.verifyElementsCount(response, '', 1)

WS.verifyElementPropertyValue(response, '[0].name', name1)

WS.sendRequestAndVerify(findTestObject('Background Tasks/Delete/Delete Background Task By Id', [('id') : id1]))

WS.sendRequestAndVerify(findTestObject('Background Tasks/Delete/Delete Background Task By Id', [('id') : id2]))