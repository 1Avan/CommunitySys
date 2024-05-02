//定义数据类型,登录接口需要携带参数ts类型
export interface loginForm {
  username: string
  password: string
}
//自定义数据类型,登录成功有token没有message登录失败有message没有token
type dataType = {
  token?: string
  msg?: string
  username?: any
  id?: BigInt
  roleId?:Number
}
//登录接口返回数据类型
export interface loginResponseDate {
  code: number
  data: dataType
  msg?: string
}

//定义服务器返回用户信息相关的数据类型
export interface userResponseData {
  code: number
  data: any
}

//定义登录数据类型
export interface registerForm {
  username: string
  password: string
}
