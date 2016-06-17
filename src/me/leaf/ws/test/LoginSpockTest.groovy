package me.leaf.ws.test

import spock.lang.Unroll
import wslite.soap.*
import spock.lang.Specification



class LoginSpockTest extends Specification {


    def WebLogin = { userName, password, serverURL ->
        def client = new SOAPClient("${serverURL}/PresenterTestSessionService/LeafDBSvc.Service1.svc")
        def response = client.send(SOAPAction: 'http://tempuri.org/LeafDbSvc/WebLogin') {
            version SOAPVersion.V1_2
            envelopeAttributes 'xmlns:tns': 'http://tempuri.org/',
                    'xmlns:wsa': 'http://www.w3.org/2005/08/addressing',
                    'xmlns:xsd': 'http://www.w3.org/2001/XMLSchema',
                    'xmlns:xsi': 'http://www.w3.org/2001/XMLSchema-instance',
                    'xmlns:env': 'http://www.w3.org/2003/05/soap-envelope',
                    'xmlns:arr': 'http://schemas.microsoft.com/2003/10/Serialization/Arrays',
                    'xmlns:n0': 'www.leaf.me/Presenter',
                    'xmlns:leaf': 'http://schemas.datacontract.org/2004/07/LeafDBSvc'
            header() {
                //order is important
                'n0:SecurityToken'("024b263e53ed1a4e-5a43d328-479743f5-bc3ba331-8adc6a5ba0c98")
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


    void 'login into web apps with valid credentials'() {
        given:
        def serverURL = "http://ws.leaftest.me"
        when:
        def loginWithValidCredentials = WebLogin('meltem+master@vngrs.com', 'Meltem123', serverURL)
        then:
        200 == loginWithValidCredentials.httpResponse.statusCode
        '4216' == loginWithValidCredentials.WebLoginResponse.WebLoginResult.text()
    }

    @Unroll('password is wrong')
    void 'login into web app with invalid credentials'(){
        given:
        def serverURL = "http://ws.leaftest.me"
        when:
        def loginWithValidCredentials = WebLogin('meltem+master@vngrs.com', 'meltem321', serverURL)
        then:
        200 == loginWithValidCredentials.httpResponse.statusCode
        '-1' == loginWithValidCredentials.WebLoginResponse.WebLoginResult.text()
    }
}

