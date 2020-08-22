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
//act
def generator = { String alphabet, int n ->
	new Random().with {
	  (1..n).collect { alphabet[ nextInt( alphabet.length() ) ] }.join()
	}
  }

def logsResponse = WS.sendRequestAndVerify(findTestObject('Background Task Executions Logs/Get Background Task Executions Logs'))

def logsCount = WS.getElementsCount(logsResponse, '')

def message 

if (logsCount!=0){
	def boolean isFound = true
	while (isFound){
		message = generator( (('a'..'z')+('0'..'9')).join(), 9 )+'message/withspecchars'
		isFound = false
		for (def i=0;i<logsCount;i++){
			if (WS.getElementPropertyValue(logsResponse, "[${i}].message")==message){
				isFound = true
				break
			}
		}
	}
}
else {
	message = generator( (('a'..'z')+('0'..'9')).join(), 9 )+'message/withspecchars'
}

def filterByNonExistMsg = "?Filters=message==$message"

// act
def getLogsResponse = WS.sendRequest(findTestObject('Background Task Executions Logs/Get Background Task Executions Logs', [('queryString') : filterByNonExistMsg]))

//assert
WS.verifyResponseStatusCode(getLogsResponse, 200)

assert WS.getElementsCount(getLogsResponse, '')==0