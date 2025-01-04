export function generateUniqueCode(prefix = "") {
    const timestamp = new Date().getTime();  // 当前时间戳
    const random = Math.floor(Math.random() * 1000);  // 随机数
    return `${prefix}-${timestamp}-${random}`;
}
