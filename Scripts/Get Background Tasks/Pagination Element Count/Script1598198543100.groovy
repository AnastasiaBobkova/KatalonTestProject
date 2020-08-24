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

def size = TotalElementCount as Integer

def ids = new ArrayList<String>(); 

for(def i = 0; i < size; i++)
{
	def id = UUID.randomUUID().toString();
	ids.add(id) // add variable value id to the list of ids
	def methodName = "Antonina${i}";
	WS.sendRequestAndVerify(findTestObject('Background Tasks/Create/Create Background Task', [
		('id') : id,
		('methodName') : methodName 
		]))
}

def pagination = "?Page=${Page}&PageSize=${PageSize}"

def response = WS.sendRequestAndVerify(findTestObject('Background Tasks/GET/Get Background Task', [('queryString') : pagination]))

WS.verifyElementsCount(response, '', ExpectedElementCount as Integer)

for(def i = 0; i < ids.size(); i++)
{
	WS.sendRequestAndVerify(findTestObject('Background Tasks/Delete/Delete Background Task By Id', [('id') : ids[i]]))
}
