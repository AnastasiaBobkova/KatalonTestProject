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

def serviceNameToFilter = 'service1'
def methodNames = ['name1', 'name2', 'name3', 'name4']
def serviceNames = [serviceNameToFilter, serviceNameToFilter, serviceNameToFilter, 'service2']
def ids = [UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString()]

for(def i = 0; i < ids.size(); i++)
{
	WS.sendRequestAndVerify(findTestObject('Background Tasks/Create/Create Background Task', [('id') : ids[i], ('methodName') : methodNames[i]
		, ('serviceName') : serviceNames[i]]))
}

def filter = "?Filters=serviceName==${serviceNameToFilter}&Sorts=methodName"

def response = WS.sendRequestAndVerify(findTestObject('Background Tasks/GET/Get Background Task', [('queryString') : filter]))

def count = 3

WS.verifyElementsCount(response, '', count)

for(def i=0; i < count; i++)
{
	WS.verifyElementPropertyValue(response, "[${i}].methodName", methodNames[i])
}

for(def i = 0; i < ids.size(); i++)
{
	WS.sendRequestAndVerify(findTestObject('Background Tasks/Delete/Delete Background Task By Id', [('id') : ids[i]]))
}