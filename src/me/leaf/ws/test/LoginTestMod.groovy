package me.leaf.ws.test
import wslite.soap.SOAPClient
import wslite.soap.SOAPVersion



def WebLogin = {userName, password, serverURL->
    def client = new SOAPClient("${serverURL}/PresenterTestSessionService/LeafDBSvc.Service1.svc")
    def response = client.send(SOAPAction:'http://tempuri.org/LeafDbSvc/WebLogin') {
        version SOAPVersion.V1_2
        envelopeAttributes 'xmlns:tns':'http://tempuri.org/',
                'xmlns:wsa':'http://www.w3.org/2005/08/addressing',
                'xmlns:xsd':'http://www.w3.org/2001/XMLSchema',
                'xmlns:xsi':'http://www.w3.org/2001/XMLSchema-instance',
                'xmlns:env':'http://www.w3.org/2003/05/soap-envelope',
                'xmlns:arr':'http://schemas.microsoft.com/2003/10/Serialization/Arrays',
                'xmlns:n0':'www.leaf.me/Presenter',
                'xmlns:leaf':'http://schemas.datacontract.org/2004/07/LeafDBSvc'
        header() {
            //order is important
            'n0:SecurityToken'("00000000000-sometoken")
            'wsa:Action'("http://tempuri.org/LeafDbSvc/WebLogin")
        }
        body {
            WebLogin(xmlns: 'http://tempuri.org/') {
                strUserName(userName)
                strPassword(password)
            }
        }
    }
}

def serverURL = "http://ws.leaftest.me"

def loginWithValidCredentials = WebLogin('someuser','somepass', serverURL)

println loginWithValidCredentials.WebLoginResponse.WebLoginResult.text()

assert 200 == loginWithValidCredentials.httpResponse.statusCode
//it should return the user id
assert '4216' == loginWithValidCredentials.WebLoginResponse.WebLoginResult.text()
