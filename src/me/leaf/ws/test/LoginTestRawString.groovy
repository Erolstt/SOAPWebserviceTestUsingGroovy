package me.leaf.ws.test

import wslite.soap.*


def client = new SOAPClient('http://ws.some.me/PresenterTestSessionService/LeafDBSvc.Service1.svc')
def response = client.send(
        """<env:Envelope xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:tns="http://tempuri.org/" xmlns:env="http://www.w3.org/2003/05/soap-envelope" xmlns:wsa="http://www.w3.org/2005/08/addressing" xmlns:arr="http://schemas.microsoft.com/2003/10/Serialization/Arrays" xmlns:n0="www.leaf.me/Presenter" xmlns:leaf="http://schemas.datacontract.org/2004/07/LeafDBSvc">
 <env:Header>
    <n0:SecurityToken>024b263e53ed1a4e-5a43d328-479743f5-bc3ba331-8adc6a5ba0c98</n0:SecurityToken>
    <wsa:Action>http://tempuri.org/LeafDbSvc/WebLogin</wsa:Action>
  </env:Header>
   <env:Body>
      <tns:WebLogin>
         <!--Optional:-->
         <tns:strUserName>someUsername</tns:strUserName>
         <!--Optional:-->
         <tns:strPassword>somepass</tns:strPassword>
      </tns:WebLogin>
   </env:Body>
</env:Envelope>"""
)
println response.WebLoginResponse.WebLoginResult.text()
assert 200 == response.httpResponse.statusCode
//it should return the user id
assert '4216' == response.WebLoginResponse.WebLoginResult.text()


