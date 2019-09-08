# RaspberryPiMail

### Raspberry Pi sends IP address Mail

### Quick start

[Download Releases](https://github.com/min-li/RaspberryPiMail/releases)

1. Rename the file as mail.jar

2. Move the file to the directory you need(eg:/usr/mail)

3. As follows

Create file 'mail.properties'

```
cd /usr/mail
touch mail.properties
```

Add the following to the file(Only test 163 mail)

```
mail.smtp.host=smtp.163.com
mail.smtp.port=25
mail.smtp.auth=true

//your email
email=
//mail authorization password
email.password=
//receiver email
receiver.email=

email.first.subject=Raspberry Pi First Boot
email.first.text=Raspberry Pi First Boot,IP Address : $ip
email.boot.subject=Raspberry Pi Reboot
email.boot.text=Raspberry Pi Reboot,IP Address : $ip
email.change.subject=Raspberry Pi IP Change
email.change.text=Raspberry Pi IP Change,IP Address : $ip

//detection time
email.time=3600000
//Detection website
email.ip=http://ip.cip.cc
```

Modify boot start item

```
sudo vim /etc/rc.local
```

Add the following to the 'exit0'

```
cd /usr/mail
nohup java -jar /usr/mail/mail.jar > /usr/mail/mail.log 2>&1 &
```

Restart your Raspberry Pi

## 关于

第一次尝试用英文写README，水平有限，希望已经表述清楚。

这个小工具可以帮助拥有公网动态IP的人实时了解IP地址的变化情况。目前已经在树莓派3B+上测试通过，理论上任何一台安装了Java环境的机器都可以使用，请自行测试。
