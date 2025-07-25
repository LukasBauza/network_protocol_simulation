# OSPF Protocol Simulation

- This project develops a network simulation protocol in Java, starting with Internet Control Message
Protocol (ICMP) ping functionality and Open Shortest Path First (OSPF) implementation. The goal
is to simulate network interactions, analysing packets and implementing network devices.

- The simulation is structured using object-oriented programming (OOP) principles, with core
components such as PC, Router, and Device classes. Features include Address Resolution Protocol
(ARP) for managing IP to Media Access Control (MAC) address resolution, Internet Protocol (IP)
packet handling, Internet Control Message Protocol (ICMP) packet sending, network device
management, and Network Interface Cards (NICs) for holding IP address details and randomly
generated MAC addresses to simulate realistic network behaviour, with the added benefit of
subnetting through the use of subnet masks. Parts of it are implemented using a command line
interface (CLI), and a graphical user interface (GUI).

- OSPF is implemented by modelling the network as a graph and calculating the lowest-cost path
between devices using Dijkstra’s algorithm, although within the application it is done from a more
simplified approach. Animations were used to represent packets being sent and received across the
network. This has all been done with the use of the Swing GUI widget toolkit for Java.

# CLI Iteration
## Starting Screen
<img width="688" height="232" alt="image" src="https://github.com/user-attachments/assets/db2bebc3-a374-46cf-a64d-51cbb19b214c" />

## Creating a PC
<img width="692" height="311" alt="image" src="https://github.com/user-attachments/assets/4774f494-b9f3-42c8-b4bc-fef95dd3dd01" />

## Creating a Router
<img width="692" height="247" alt="image" src="https://github.com/user-attachments/assets/11254e69-7776-417d-912c-f25242757add" />

## PC Sub-Menu
<img width="764" height="247" alt="image" src="https://github.com/user-attachments/assets/95b29f9c-a003-4cf2-9814-b6a681983b40" />

## Router Sub-Menu
<img width="813" height="343" alt="image" src="https://github.com/user-attachments/assets/9d7e4d63-4b07-4ae6-84fa-d52a6b72c4e5" />

## Setting an IP Address on a Router's NIC
<img width="813" height="383" alt="image" src="https://github.com/user-attachments/assets/e1c2b581-9643-490b-9e0a-c0513a3213f8" />

## ARP Request/Reply
<img width="813" height="486" alt="image" src="https://github.com/user-attachments/assets/e5e13825-4905-4949-85d4-b32b310f754b" />

## IP and ICMP Packets
<img width="813" height="711" alt="image" src="https://github.com/user-attachments/assets/05c776f4-8754-4956-9f18-5e5b854b571e" />

# GUI Iteration
## Sending Packet Accross Devices
<img width="1322" height="1122" alt="image" src="https://github.com/user-attachments/assets/513dcf8c-30a5-4c64-8721-82f5f5b35d10" />
