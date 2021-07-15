# springboot-oauth2-application
springboot-oauth2-application



I will put a daily scene: payment with a credit card in a store. In this case, there are three partners: the store, the bank, and us. Something similar happens in the OAuth2 protocol. These are the steps:

The client, or the buyer, asks the bank for a credit card. Then, the bank will collect our information, verify who we are, and provide us a credit card, depending on the money we have in our account or if it tells us not to waste time. In the OAuth2 protocol that grants the cards, it is called the Authentication Server.
If the bank has given us the card, we can go to the store, i.e. the web server, and we present the credit card. The store does not owe us anything, but they can ask the bank, through the card reader, if they can trust us and to what extent (the credit balance). The store is the Resource Server.
The store, depending on the money that the bank says we have, will allow us to make purchases. In the OAuth2 analogy, the web server will allow us to access  pages, depending on our financial status.
In case you have not noticed that authentication servers are usually used, when you go to a webpage and are asked to register, but as an option, it lets you do it through Facebook or Google, you are using this technology. Google or Facebook becomes the 'bank' that issues the 'card.' The webpage that asks you to register will use it to verify that you have 'credit' and let you enter.


 Access Token vs Refresh Token
An access token is a string representing an authorization issued to the client. Tokens represent specific scopes and durations of access, granted by the resource owner, and enforced by the resource server and authorization server.

Refresh token is issued (along with access token) to the client by the authorization server, and it is used to obtain a new access token when the current access token becomes invalid or expires. The refresh token is also used to get additional access tokens with identical or narrower scope (access tokens may have a shorter lifetime and fewer permissions than authorized by the resource owner). Issuing a refresh token is optional at the discretion of the authorization server.

The responsibility of access token is to access data before it gets expired.
The responsibility of refresh token is to request for a new access token when the existing access token is expired.

clientId – (required) the client id.
secret – (required for trusted clients) the client secret, if any.
scope – The scope to which the client is limited. If the scope is undefined or empty (the default), the client is not limited by scope.
authorizedGrantTypes – Grant types that are authorized for the client to use. The default value is empty.


