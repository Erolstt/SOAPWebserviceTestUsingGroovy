package me.leaf.ws.test

import wslite.soap.*

//http://ws.leaftest.me/PresenterTestSessionService/LeafDBSvc.Service1.svc?wsdl
//http://ws.leaftest.me/PresenterTestSessionService/LeafDBSvc.Service1.svc
def client = new SOAPClient('http://ws.leaftest.me/PresenterTestSessionService/LeafDBSvc.Service1.svc')
def response = client.send(SOAPAction: 'http://tempuri.org/LeafDbSvc/WebLogin') {

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
        'n0:SecurityToken'("024b263e53ed1a4e-5a43d328-479743f5-bc3ba331-8adc6a5ba0c98")
        'wsa:Action'("http://tempuri.org/LeafDbSvc/WebLogin")
    }
    body {
        WebLogin(xmlns: 'http://tempuri.org/'){
            strUserName('meltem+master@vngrs.com')
            strPassword('Meltem123')
        }
    }
}

println response.WebLoginResponse.WebLoginResult.text()
assert 200 == response.httpResponse.statusCode
//it should return the user id
assert '4216' == response.WebLoginResponse.WebLoginResult.text()





