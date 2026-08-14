export interface PageResponse<T> {
  content: T[];
  totalElements: number;
  pageSize: number;
  pageNumber: number;
}
