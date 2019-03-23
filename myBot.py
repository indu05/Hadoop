import nltk
import numpy as np
import random
import string 
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity
from sklearn.metrics import pairwise_distances_chunked
import math
questions = {}

f=open('C:\\Users\\HP PC\\Untitled Folder\\chatbot.txt')
raw=f.read()
raw=raw.lower()# converts to lowercase 
sent_tokens = nltk.sent_tokenize(raw)# converts to list of sentences 
word_tokens = nltk.word_tokenize(raw)# converts to list of words

lemmer = nltk.stem.WordNetLemmatizer()
def LemTokens(tokens):
    return [lemmer.lemmatize(token) for token in tokens]
remove_punct_dict = dict((ord(punct), None) for punct in string.punctuation)
def LemNormalize(text):
    return LemTokens(nltk.word_tokenize(text.lower().translate(remove_punct_dict)))
  

GREETING_INPUTS = ("hello", "hi", "greetings", "sup", "what's up","hey",)
GREETING_RESPONSES = ["hi", "hey", "*nods*", "hi there", "hello", "I am glad! You are talking to me"]
MORE_ANSWERS = ["more", "next" , "anything else" , "yes", "ok"]
CONNECTING_STATMENTS = ['hmm', 'ouch', 'oops', 'fine']
EXCALMAT_STATEMENTS = ['funny', 'woow', 'good']

def greeting(sentence):
 
    for word in sentence.split():
        if word.lower() in GREETING_INPUTS:
            return random.choice(GREETING_RESPONSES)
        
def getTheIterIndexOfQues(ques):
    print(ques)
    print(questions[ques])
    cnt = questions[ques]
    return math.ceil((cnt/5))*5
            
    
    def response(user_sent_tokens):  
    print('quest ', user_sent_tokens)
    sent_tokens.append(user_sent_tokens) 
    TfidfVec = TfidfVectorizer(tokenizer=LemNormalize, stop_words='english') 
    tfidf = TfidfVec.fit_transform(sent_tokens)
    vals = cosine_similarity(tfidf, tfidf[tfidf.shape[0]-1]) 
    answerCnt = getTheIterIndexOfQues(user_sent_tokens)
    a = vals.argsort(axis=0)[::-1][:answerCnt]
    tp = tuple(a.reshape(1, -1)[0]) 
    ntp = {}
    i=0 
    for ele in tp:        
        if (i==0):
            print('Results :')
        else: 
            if(vals[ele][0] > 0):                
                ntp[i]=sent_tokens[ele]                  
        i=i+1
    sent_tokens.pop(tfidf.shape[0]-1) 
    return ntp 
  
  def prepareResponse(result,user_response):    
    if(questions[user_response] in result):
        print(result[questions[user_response]])
    else:
        questions.pop(user_response)
        print('Sorry, refine your question')
    
     def isQuestionBeenAsked(user_response):
    preparedQs = ""
    questionAsked = user_response.lower();
    if(questionAsked in MORE_ANSWERS):
        quesList= list(questions.keys()) 
        print(quesList)
        if(len(quesList) > 0):
            lastQuestIndex = len(quesList)-1
            print('lastQuestIndex', lastQuestIndex)
            lastQuesAnsweredIdx = questions[quesList[lastQuestIndex]] 
            print('lastQuesAnsweredIdx', lastQuesAnsweredIdx)
            questions[quesList[lastQuestIndex]] = lastQuesAnsweredIdx + 1
            print('quesList[lastQuestIndex]', quesList[lastQuestIndex])
            preparedQs = quesList[lastQuestIndex]
        else :
            preparedQs = "BREAK_NO_PREV_QS"
        
    elif (questionAsked in questions):
        questions[questionAsked] =  questions[questionAsked] +1
        preparedQs = questionAsked
    else :
        questions[user_response] = 1
        preparedQs = user_response.lower()
 
    print(questions)
    print('prev qs' , preparedQs)
    return preparedQs
  
  def isNotValidQs(user_response):
    return (user_response == 'BREAK_NO_PREV_QS')
        
    
flag=True
print("ROBO: My name is Robo. I will answer your queries about WCADS. If you want to exit, type Bye!")
while(flag==True): 
    user_response = input()
    user_response=user_response.lower()
    if(user_response!='bye'):
        if(user_response=='thanks' or user_response=='thank you' ):
            flag=False
            print("ROBO: You are welcome..")
        else:
            if(greeting(user_response)!=None):
                print("ROBO: "+greeting(user_response))
            else:                
                if(user_response in CONNECTING_STATMENTS):
                    print("ROBO: Do you like my response ?")
                elif(user_response in EXCALMAT_STATEMENTS):
                        print("ROBO: Thank you, say ok to continue")
                
                else :
                    user_response = isQuestionBeenAsked(user_response)
                    if(isNotValidQs(user_response)):                     
                        print('ROBO: Please start a conversation about you want to know !!')
                    else:
                        result = response(user_response) 
                        prepareResponse(result,user_response)                
    else:
        flag=False
        print("ROBO: Bye! take care..")    
