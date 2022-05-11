const abbrToBuilding = {
    H1: '第一教学楼',
    H2: '第二教学楼',
    H3: '第三教学楼',
    H4: '第四教学楼',
    H5: '第五教学楼',
    H6: '第六教学楼',
    HGX: '光华楼西辅楼',
}
const buildingToAbbr = {
    第一教学楼: 'H1',
    第二教学楼: 'H2',
    第三教学楼: 'H3',
    第四教学楼: 'H4',
    第五教学楼: 'H5',
    第六教学楼: 'H6',
    光华楼西辅楼: 'HGX',
}
const periods = [
    '周日','周一','周二','周三','周四','周五','周六',
]
const semesters = ['春','秋']
const years = [
    '2010-2011',
    '2011-2012',
    '2012-2013',
    '2013-2014',
    '2014-2015',
    '2015-2016',
    '2016-2017',
    '2017-2018',
    '2018-2019',
    '2019-2020',
    '2020-2021',
    '2021-2022'
]
const CLOSE = '当前不在选课时间段内'
const ONE_ON = '一轮选课开始'
const ONE_OFF = '一轮选课结束'
const TWO_ON = '二轮选课开始'
const TWO_OFF = '二轮选课结束'
export default
{
    buildingToAbbr,
    abbrToBuilding,
    periods,
    semesters,
    years,
    CLOSE,
    ONE_OFF,
    ONE_ON,
    TWO_OFF,
    TWO_ON
}