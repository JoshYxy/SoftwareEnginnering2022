export function setCourseTime(data, e) {
    var periods =['周日','周一','周二','周三','周四','周五','周六']
    for(let i = 0; i < e.length; i++) {
        if(e[i] != null) {
            e[i].sort(function(a,b){
                if(a.length != b.length) return a.length - b.length
                else return a - b
            })
        }
    }
    data.times = e
    data['classHours'] = 0
    var flag = false//是否多节连上
    var k, j
        data.courseTime = ''
        for(let i in data.times) {
            j = data.times[i]
            k = 0
            flag = false
            if(j.length > 0) {
                data.courseTime += periods[parseInt(i)]+': '
                while(k < j.length){
                    data.classHours++
                    if(k == 0) data.courseTime += j[k]
                    else if(parseInt(j[k]) != parseInt(j[k - 1]) + 1){
                        if(flag) data.courseTime += '-'+j[k-1]+','+j[k]
                        else data.courseTime += ',' + j[k]
                        flag = false
                    }
                    else flag = true
                    k++
                }
                k--
                if(flag) data.courseTime += '-'+j[k]+'; '
                else data.courseTime += '; '
            }
        }
}