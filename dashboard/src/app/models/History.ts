export interface History {
    id: number
    transDate: Date
    transType: string
    amount: number | string
    balance: number
    targetId: string
    message: string
}
