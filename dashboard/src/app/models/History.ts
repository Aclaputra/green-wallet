export interface History {
    id:string;
    merchant: string;
    transDetail: {
        amount: number;
        description: string;
        type: string;
        curr_balance: number;
        source_id: string;
        created_at: Date;
    };
    user: {
        id: string;
        name: string;
        phone: string;
        created_at: Date;
        updated_at: Date;
    };
}
