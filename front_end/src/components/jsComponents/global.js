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

export default
{
    buildingToAbbr,
    abbrToBuilding,
    periods
}