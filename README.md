# cbh
看病要看什麼科 line  chatbot 測試。   </br>
 
功能：     </br>
     輸入病狀,回復可能症狀的科別  </br>
     輸入地點,回復可能醫院位置    </br>



系統架構說明             </br>

![image] (https://github.com/tommy770221/cbh/blob/master/%E7%9C%8B%E7%97%85%E8%A6%81%E7%9C%8B%E4%BB%80%E9%BA%BC%E7%A7%91chatbot.png)



目錄說明：
 
src/main/java/com/cbh/web   spring mvc controller        </br>
src/main/java/com/cbh/service   spring data repository   </br>
src/main/java/com/cbh/mongo      mongodb entity          </br>
src/main/java/com/cbh/entity    spring roo entity        </br>
src/main/java/com/cbh/geo/util/GeoUtil.java  醫院地址定位  </br>
src/main/java/com/cbh/facebook/util/         facebook chatbot managment   </br> 
src/main/java/com/cbh/web/FacebookWebhookController.java   facebook  chatbot  </br>
src/main/java/com/cbh/web/LineBotController.java           line chatbot </br>
src/main/resources/chatbottest2.sql           整理好的病狀資料 </br>
src/main/resources/病症分詞.txt   java結巴分詞訓練語料檔   </br>
src/main/resources/META-INF/persistence.xml    DB相關設定 </br>
src/main/resources/META-INF/spring/applicationContext.xml    spring 設定檔 </br>
src/main/webapp/WEB-INF/layouts/      tiles layout template設定 </br>
src/main/webapp/WEB-INF/views/*/views.xml   tiles 設定  </br>




打包war檔           </br>
mvn clean package  </br>

測試chatbot帳號 ： 
![image](https://qr-official.line.me/M/QC85t_n-_U.png) </br>
