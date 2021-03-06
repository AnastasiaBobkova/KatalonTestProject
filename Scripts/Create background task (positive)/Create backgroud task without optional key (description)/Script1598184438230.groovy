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

def methodName = 'someName'

def declaringTypeFullName = 'Suvoda.SuperNamespace.SuperClass'

def serviceName = 'SubjectService'

// act
def response = WS.sendRequestAndVerify(findTestObject('Background Tasks/Create/Create Background Task without optional key (description)', 
        [('id') : id, ('methodName') : methodName , ('declaringTypeFullName') : declaringTypeFullName,
			, ('serviceName') : serviceName]))
//assert
def createdTask = WS.sendRequestAndVerify(findTestObject('Background Tasks/GET/Get Background Task By Id', [('URL') : GlobalVariable.URL
            , ('id') : id]))

WS.verifyElementPropertyValue(createdTask, 'name', 'some name')

WS.verifyElementPropertyValue(createdTask, 'description', null)

//clean
WS.sendRequestAndVerify(findTestObject('Background Tasks/Delete/Delete Background Task By Id', [('URL') : GlobalVariable.URL, ('id') : id]))