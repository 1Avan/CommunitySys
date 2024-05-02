export const getTime = () => {
  let message = ''
  //通过内置的构造函数Date获取当前时间
  let hours = new Date().getHours()
  if (hours <= 9) {
    message = '早上'
  } else if (hours <= 12) {
    message = '中午'
  } else if (hours <= 18) {
    message = '下午'
  } else {
    message = '晚上'
  }
  return message
}

// 将 ISO 格式的日期时间字符串转换为指定格式的字符串
export const formatDateTime = (isoDateTime: any) => {
  //如果对象为空返回null
  if (isoDateTime == null) {
    return null
  }
  // 创建 Date 对象并解析 ISO 格式的日期时间字符串
  let date = new Date(isoDateTime)

  // 提取年、月、日、时、分、秒
  let year = date.getFullYear()
  let month = String(date.getMonth() + 1).padStart(2, '0')
  let day = String(date.getDate()).padStart(2, '0')
  let hours = String(date.getHours()).padStart(2, '0')
  let minutes = String(date.getMinutes()).padStart(2, '0')
  let seconds = String(date.getSeconds()).padStart(2, '0')

  // 格式化为 "yyyy-MM-dd HH:mm:ss" 形式的字符串
  let formattedDateTime = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`

  return formattedDateTime
}
