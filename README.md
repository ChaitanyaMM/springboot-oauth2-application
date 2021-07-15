# springboot-oauth2-application
springboot-oauth2-application



I will put a daily scene: payment with a credit card in a store. In this case, there are three partners: the store, the bank, and us. Something similar happens in the OAuth2 protocol. These are the steps:

The client, or the buyer, asks the bank for a credit card. Then, the bank will collect our information, verify who we are, and provide us a credit card, depending on the money we have in our account or if it tells us not to waste time. In the OAuth2 protocol that grants the cards, it is called the Authentication Server.
If the bank has given us the card, we can go to the store, i.e. the web server, and we present the credit card. The store does not owe us anything, but they can ask the bank, through the card reader, if they can trust us and to what extent (the credit balance). The store is the Resource Server.
The store, depending on the money that the bank says we have, will allow us to make purchases. In the OAuth2 analogy, the web server will allow us to access  pages, depending on our financial status.
In case you have not noticed that authentication servers are usually used, when you go to a webpage and are asked to register, but as an option, it lets you do it through Facebook or Google, you are using this technology. Google or Facebook becomes the 'bank' that issues the 'card.' The webpage that asks you to register will use it to verify that you have 'credit' and let you enter.


