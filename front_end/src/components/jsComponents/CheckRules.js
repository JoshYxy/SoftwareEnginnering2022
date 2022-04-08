export function validName(rule, value, callback){
    const reg = /^[A-Za-z\u4E00-\u9FA5]+$/
    if (reg.test(value)) {
        callback()
      } else {
        return callback(new Error('姓名必须为汉字或英语'))
      }
}

export function validPhone(rule, value, callback){
    if (value.length != 11 && value.length != 0) {
      callback(new Error('电话号码必须为11位'))
    } else{
        const reg = /^1[3|4|5|7|8][0-9]\d{8}$/
        if (reg.test(value) || value.length == 0) {
            callback()
          } else {
            return callback(new Error('手机号码必须为1开头的符合规则的11位数字'))
          }
      }
  }
export function validPassport(rule, value, callback){
    if (value.length != 18) {
      callback(new Error('身份证必须为18位'))
    } else{
        const reg =  /(^\d{18}$)|(^\d{17}(\d|X|x)$)/
        if (reg.test(value)) {
            callback()
          } else {
            return callback(new Error('身份证号不符合规则'))
          }
      }
  }

export function validEmail(rule, value, callback){
    const reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((.[a-zA-Z0-9_-]{2,3}){1,2})$/
    if (reg.test(value) || value.length == 0) {
        callback()
      } else {
        return callback(new Error('邮箱地址不符合规则'))
      }
  }

export function validTimetable(rule, value, callback){
    var flag = false
    for(let i = 0; i < value.length; i++){
      if(value[i].length > 0){
        flag = true
      }
    }
    if (!flag) {
      return callback(new Error('上课时间不能为空'))
    } else{
      callback()
    }
  }
export function validSelectRoom(rule, value, callback){
    if (value == null || value[0] == null || value[0] == '') {
      return callback(new Error('请选择上课教室'))
    } else{
      callback()
    }
  }
export function validTeacher(rule, value, callback){
    if (value == null || value.name == null || value.name == '') {
      return callback(new Error('请选择任课教师'))
    } else{
      callback()
    }
  }