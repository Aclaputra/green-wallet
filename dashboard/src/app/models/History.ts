export interface History {
    id: string
    transDate: Date
    transType: string
    amount: number | string
    balance: number
    targetId: string
    targetName: string
    message: string
}
