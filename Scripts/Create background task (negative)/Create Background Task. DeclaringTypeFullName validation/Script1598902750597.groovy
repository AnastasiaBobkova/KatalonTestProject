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

// arrange
def id = UUID.randomUUID().toString()

def name = 'new background task'

def description = 'first backgroundTask'

def methodName = 'DoJob'

def serviceName = 'SubjectService'

def statusCode = expectedStatusCode as int

// act
def response = WS.sendRequest(findTestObject('Background Tasks/Create/Create Background Task', [('URL') : GlobalVariable.URL, ('id') : id
			, ('name') : name, ('description') : description, ('methodName') : methodName, ('declaringTypeFullName') : declaringTypeFullName
			, ('serviceName') : serviceName]))

// assert
WS.verifyResponseStatusCode(response, statusCode)

// clear
if(statusCode == 200)
{
WS.sendRequestAndVerify(findTestObject('Background Tasks/Delete/Delete Background Task By Id', [('URL') : GlobalVariable.URL
			, ('id') : id]))
}