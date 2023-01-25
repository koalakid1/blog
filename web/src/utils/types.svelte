<script context="module" lang="ts">
    export type Board = {
        id: number,
        writer: string,
        title: string,
        content: string,
        registerDate: Date,
        updateDate?: Date,
        deleteDate?: Date,
    }
</script>